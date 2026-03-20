package com.hustle.rag_workspace_ms.service.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface DocumentService {

    InputStream downloadDocument(String minioKey);

    void save(String absoluteFilePath, MultipartFile document);

    void delete(String absoluteFilePath);
}
