package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Workspace;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    @Modifying
    @Query("UPDATE Workspace w SET w.isFavourite = :isFavourite WHERE w.id = :id")
    void updateFavouriteById(@Param("id") UUID id, @Param("isFavourite") Boolean isFavourite);
}
