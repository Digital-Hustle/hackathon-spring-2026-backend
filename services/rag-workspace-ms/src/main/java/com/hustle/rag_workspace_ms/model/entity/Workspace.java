package com.hustle.rag_workspace_ms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// TODO обавить favourite boolean
//  sizeType enum BIG MEDIUM SMALL
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "workspace", schema = "rag")
public class Workspace {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column
    private String icon;

    @Column
    private String type;

    @Column
    private LocalDateTime createdAt;
}
