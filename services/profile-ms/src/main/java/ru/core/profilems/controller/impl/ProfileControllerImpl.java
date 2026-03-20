package ru.core.profilems.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.core.profilems.controller.ProfileController;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.dto.request.SearchParametersRq;
import ru.core.profilems.dto.response.PageRs;
import ru.core.profilems.mapper.ProfileMapper;
import ru.core.profilems.mapper.RequestParamsMapper;
import ru.core.profilems.model.SearchFilter;
import ru.core.profilems.model.entity.Profile;
import ru.core.profilems.service.entity.ProfileService;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final RequestParamsMapper requestParamsMapper;

    @Override
    public ResponseEntity<PageRs<ProfileDto>> getProfiles(Integer page, Integer size) {
        Page<Profile> pageEntity = profileService.getAll(page, size);
        PageRs<ProfileDto> response = toPageResponse(pageEntity, profileMapper::convert);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<PageRs<ProfileDto>> searchProfiles(SearchParametersRq searchParametersRq) {
        SearchFilter searchFilter = requestParamsMapper.convert(searchParametersRq);

        Page<Profile> pageEntity = profileService.search(searchFilter);
        PageRs<ProfileDto> response = toPageResponse(pageEntity, profileMapper::convert);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ProfileDto> getProfileById(UUID profileId) {
        Profile profile = profileService.getById(profileId);
        ProfileDto profileDto = profileMapper.convert(profile);

        return ResponseEntity.ok().body(profileDto);
    }

    @Override
    public ResponseEntity<ProfileDto> createProfile(ProfileDto profileDto) {
        Profile profile = profileMapper.convert(profileDto);
        profile = profileService.create(profile);

        return ResponseEntity
                .created(URI.create("/api/v1/profile/" + profile.getId()))
                .body(profileMapper.convert(profile));
    }

    @Override
    public ResponseEntity<ProfileDto> updateProfile(UUID profileId, ProfileDto profileDto) {
        Profile profile = profileMapper.convert(profileDto);
        profile = profileService.update(profileId, profile);

        return ResponseEntity.ok().body(profileMapper.convert(profile));
    }

    @Override
    public void deleteProfile(UUID profileId) {
        profileService.delete(profileId);
    }

    private <T, R> PageRs<R> toPageResponse(Page<T> pageEntity, Function<T, R> mapper) {
        List<R> profiles = pageEntity.getContent().stream()
                .map(mapper)
                .toList();

        return PageRs.<R>builder()
                .content(profiles)
                .totalPages(pageEntity.getTotalPages())
                .totalElements(pageEntity.getTotalElements())
                .curPage(pageEntity.getNumber() + 1)
                .pageSize(pageEntity.getSize())
                .build();
    }
}
