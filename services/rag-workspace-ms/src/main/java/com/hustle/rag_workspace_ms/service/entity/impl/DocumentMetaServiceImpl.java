package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.repository.DocumentMetaRepository;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentMetaServiceImpl implements DocumentMetaService {

    private final DocumentMetaRepository documentMetaRepository;

    @Override
    public List<DocumentMeta> getAllByOwnerId(UUID workspaceId) {
        return documentMetaRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public DocumentMeta getById(UUID id) {
        return documentMetaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public DocumentMeta create(DocumentMeta photoMetaInfo) {
        return documentMetaRepository.save(
                photoMetaInfo.toBuilder()
                        .id(UUID.randomUUID())
                        .build()
        );
    }

    @Override
    public DocumentMeta update(UUID id, DocumentMeta photoMetaInfo) {
        return documentMetaRepository.save(
                photoMetaInfo.toBuilder()
                        .id(id)
                        .build()
        );
    }

    @Transactional
    @Override
    public void updateActivity(UUID id, Boolean isActive) {
        documentMetaRepository.updateActivity(id, isActive);
    }

    @Override
    public void delete(UUID id) {
        documentMetaRepository.deleteById(id);
    }

    @Override
    public boolean existsByWorkspaceId(UUID workspaceId) {
        return documentMetaRepository.existsByWorkspaceId(workspaceId);
    }
}
