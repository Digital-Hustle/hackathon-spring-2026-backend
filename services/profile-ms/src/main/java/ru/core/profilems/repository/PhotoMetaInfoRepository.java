package ru.core.profilems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.core.profilems.model.entity.PhotoMetaInfo;

import java.util.Optional;
import java.util.UUID;

public interface PhotoMetaInfoRepository extends JpaRepository<PhotoMetaInfo, UUID> {

    Optional<PhotoMetaInfo> findByProfileId(UUID profileId);

    boolean existsByProfileId(UUID profileId);
}
