package ru.digital_hustle.obCIdian.workspace_processor_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digital_hustle.obCIdian.workspace_processor_ms.model.SummaryEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface SummaryRepository extends JpaRepository<SummaryEntity, UUID> {
    List<SummaryEntity> findAllByWorkspaceIdOrderByCreatedAtDesc(UUID workspaceId);

    void deleteAllByWorkspaceId(UUID workspaceId);
}
