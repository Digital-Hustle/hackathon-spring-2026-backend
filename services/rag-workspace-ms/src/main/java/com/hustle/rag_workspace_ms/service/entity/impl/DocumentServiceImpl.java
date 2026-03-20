package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.gateway.MinioGateway;
import com.hustle.rag_workspace_ms.service.entity.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final MinioGateway minioGateway;

    @Override
    public InputStream downloadDocument(String minioKey) {
        return minioGateway.downloadDocument(minioKey);
    }

    @Override
    public void save(String absoluteFilePath, MultipartFile photo) {
        minioGateway.saveDocument(absoluteFilePath, photo);
    }

    @Override
    public void delete(String absoluteFilePath) {
        minioGateway.deleteDocument(absoluteFilePath);
    }
}
