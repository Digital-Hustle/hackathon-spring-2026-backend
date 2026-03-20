package ru.core.profilems.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.core.profilems.constants.PhotoConstants;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "photos", schema = "profile")
public class PhotoMetaInfo {

    @Id
    private UUID id;

    @Column(name = "profile_id", nullable = false)
    private UUID profileId;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "extension", nullable = false, length = PhotoConstants.PHOTO_EXTENSION_LENGTH)
    private String extension;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "content_type", length = PhotoConstants.CONTENT_TYPE_LENGTH)
    private String contentType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
