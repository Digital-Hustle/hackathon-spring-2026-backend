package ru.core.profilems.service.entity;

import ru.core.profilems.model.entity.PhotoMetaInfo;

import java.util.UUID;

public interface PhotoMetaInfoService {

    PhotoMetaInfo getByOwnerId(UUID ownerId);

    PhotoMetaInfo getById(UUID id);

    PhotoMetaInfo create(PhotoMetaInfo photoMetaInfo);

    PhotoMetaInfo update(UUID id, PhotoMetaInfo photoMetaInfo);

    void delete(UUID id);

    boolean existsByProfileId(UUID profileId);
}
