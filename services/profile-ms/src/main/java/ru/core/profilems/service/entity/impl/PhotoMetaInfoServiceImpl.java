package ru.core.profilems.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.core.profilems.model.entity.PhotoMetaInfo;
import ru.core.profilems.repository.PhotoMetaInfoRepository;
import ru.core.profilems.service.entity.PhotoMetaInfoService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoMetaInfoServiceImpl implements PhotoMetaInfoService {

    private final PhotoMetaInfoRepository photoMetaInfoRepository;

    @Override
    public PhotoMetaInfo getByOwnerId(UUID ownerId) {
        return photoMetaInfoRepository.findByProfileId(ownerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PhotoMetaInfo getById(UUID id) {
        return photoMetaInfoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PhotoMetaInfo create(PhotoMetaInfo photoMetaInfo) {
        return photoMetaInfoRepository.save(
                photoMetaInfo.toBuilder()
                        .id(UUID.randomUUID())
                        .build()
        );
    }

    @Override
    public PhotoMetaInfo update(UUID id, PhotoMetaInfo photoMetaInfo) {
        return photoMetaInfoRepository.save(
                photoMetaInfo.toBuilder()
                        .id(id)
                        .build()
        );
    }

    @Override
    public void delete(UUID id) {
        photoMetaInfoRepository.deleteById(id);
    }

    @Override
    public boolean existsByProfileId(UUID profileId) {
        return photoMetaInfoRepository.existsByProfileId(profileId);
    }
}
