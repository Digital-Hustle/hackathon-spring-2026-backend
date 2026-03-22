package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.factory.DocumentMetaFactory;
import com.hustle.rag_workspace_ms.factory.FileParserFactory;
import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.service.domain.AsyncDocumentProcessor;
import com.hustle.rag_workspace_ms.service.domain.DocumentProcessing;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import com.hustle.rag_workspace_ms.service.entity.DocumentService;
import com.hustle.rag_workspace_ms.utils.FileParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO
//  написать ручку на получение всех workspace +
//  ручку Кире для аудио +
//  НЕ ЗАБЫТЬ сделать ручку на удаление документа(вектора, минио + мета инфа)
//  ручка на обновление состояния активности файла
//  чат прямо в этом микросе
//  подумать ещё с Киреной ручкой для генерации подкастов
//  точно ли я должен возвращать именно весь текст всех файлов
//  (+добить логику, чтоб работать только с активными файлами, тип где статус isActive)

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentProcessingImpl implements DocumentProcessing {

    private final DocumentMetaService documentMetaService;
    private final DocumentService documentService;
    private final AsyncDocumentProcessor asyncDocumentProcessor;
    private final FileParserFactory fileParserFactory;

    @Transactional
    @Override
    public DocumentMeta processUpload(UUID workspaceId, MultipartFile document) {
        DocumentMeta documentMeta = DocumentMetaFactory.newProcessingPhotoMetaInfo(workspaceId, document);
        DocumentMeta createdDocumentMeta = documentMetaService.create(documentMeta);

        asyncDocumentProcessor.processDocument(workspaceId, createdDocumentMeta, document);

        String minioKey = "%s.%s".formatted(
                createdDocumentMeta.getId(), FilenameUtils.getExtension(document.getOriginalFilename())
        ); // TODO вообще это нужно в хелпер

        documentService.save(minioKey, document);

        return createdDocumentMeta;
    }

    @Override
    public List<DocumentText> getDocumentsContent(UUID workspaceId) {
        // TODO дописать, чтобы доставать просто по workspaceId
        List<DocumentMeta> documentsMeta = documentMetaService.getAllByOwnerId(workspaceId);
        Map<String, DocumentMeta> keyToDocumentMap = documentsMeta.stream()
                .collect(Collectors.toMap(
                        doc -> "%s.%s".formatted(doc.getId(), doc.getExtension().toString().toLowerCase()),
                        Function.identity()
                ));

        return keyToDocumentMap.entrySet().stream()
                .map(entry -> {
                    String minioKey = entry.getKey();
                    DocumentMeta documentMeta = entry.getValue();

                    return getDocumentText(minioKey, documentMeta);
                })
                .toList();
    }

    private DocumentText getDocumentText(String minioKey, DocumentMeta documentMeta) {
        try (InputStream inputStream = documentService.downloadDocument(minioKey)) {
            FileParser parser = fileParserFactory.getParser(documentMeta.getContentType());
            String extractedText = parser.extractText(inputStream, documentMeta.getOriginalName());

            return DocumentText.builder()
                    .id(documentMeta.getId())
                    .content(extractedText)
                    .build();
        } catch (Exception exception) {
            throw new RuntimeException("Parsing failed for file: " + documentMeta.getOriginalName(), exception);
        }
    }
}