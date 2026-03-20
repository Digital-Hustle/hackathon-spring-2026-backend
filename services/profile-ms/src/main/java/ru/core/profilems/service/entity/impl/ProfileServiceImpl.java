package ru.core.profilems.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.core.profilems.constants.ContextKeys;
import ru.core.profilems.constants.ErrorMessages;
import ru.core.profilems.dto.ThreadLocalMap;
import ru.core.profilems.model.SearchFilter;
import ru.core.profilems.model.entity.Profile;
import ru.core.profilems.repository.ProfileRepository;
import ru.core.profilems.service.entity.ProfileService;
import ru.digital_hustle.exceptions_starter.constant.ExceptionConstants;

import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Page<Profile> getAll(Integer page, Integer size) {
        return profileRepository.findAll(PageRequest.of(page - 1, size));
    }

    @Override
    public Page<Profile> search(SearchFilter searchFilter) {
        BiFunction<String, PageRequest, Page<Profile>> searchMethod = searchFilter.ignoreCase()
                ? profileRepository::searchAnywhereInNameOrSurnameIgnoreCase
                : profileRepository::searchAnywhereInNameOrSurname;

        PageRequest pageRq = PageRequest.of(searchFilter.page() - 1, searchFilter.size());

        return searchMethod.apply(searchFilter.query(), pageRq);
    }

    @Override
    public Profile getById(UUID profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessages.PROFILE_NOT_FOUND.formatted(profileId))
                );
    }

    @Transactional
    @Override
    public Profile create(Profile profile) {
        UUID profileId = UUID.fromString(ThreadLocalMap.get(ContextKeys.USER_ID, String.class));

        if (profileRepository.existsById(profileId)) {
            log.warn(
                    ExceptionConstants.LOG_MESSAGE,
                    ErrorMessages.PROFILE_ALREADY_EXISTS.formatted(profileId)
            );
            return getById(profileId);
        }

        Profile profileWithId = profile.toBuilder()
                .id(profileId)
                .build();

        return profileRepository.save(profileWithId);
    }

    @Transactional
    @Override
    public Profile update(UUID profileId, Profile profile) {
        Profile existedProfile = getById(profileId);

        Profile updatedProfile = existedProfile.toBuilder()
                .name(profile.getName())
                .surname(profile.getSurname())
                .build();

        return profileRepository.save(updatedProfile);
    }

    @Override
    public void delete(UUID profileId) {
        profileRepository.deleteById(profileId);
    }

    @Override
    public boolean exists(UUID profileId) {
        return profileRepository.existsById(profileId);
    }
}
