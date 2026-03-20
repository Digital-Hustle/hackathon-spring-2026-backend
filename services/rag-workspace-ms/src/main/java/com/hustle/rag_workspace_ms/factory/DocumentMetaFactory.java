package com.hustle.rag_workspace_ms.factory;

import com.hustle.rag_workspace_ms.enums.FileExtension;
import com.hustle.rag_workspace_ms.enums.FileProcessingStatus;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DocumentMetaFactory {

    public static DocumentMeta newProcessingPhotoMetaInfo(UUID workspaceId, MultipartFile file) {
        String fileNameWithExtension = file.getOriginalFilename();
        String fileName = FilenameUtils.getBaseName(fileNameWithExtension);
        String extension = FilenameUtils.getExtension(fileNameWithExtension).toUpperCase();

        return DocumentMeta.builder()
                .originalName(fileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .extension(FileExtension.valueOf(extension))
                .status(FileProcessingStatus.PENDING)
                .workspaceId(workspaceId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
