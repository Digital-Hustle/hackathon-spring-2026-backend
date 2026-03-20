package com.hustle.rag_workspace_ms.gateway;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioGateway {

    InputStream downloadDocument(String minioKey);

    void saveDocument(String absoluteFilePath, MultipartFile document);

    void deleteDocument(String absoluteFilePath);
}
