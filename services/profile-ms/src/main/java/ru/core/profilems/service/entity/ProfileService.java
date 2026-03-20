package ru.core.profilems.service.entity;

import org.springframework.data.domain.Page;
import ru.core.profilems.model.SearchFilter;
import ru.core.profilems.model.entity.Profile;

import java.util.UUID;

public interface ProfileService {

    Page<Profile> getAll(Integer page, Integer size);

    Page<Profile> search(SearchFilter searchFilter);

    Profile getById(UUID profileId);

    Profile create(Profile profile);

    Profile update(UUID profileId, Profile profile);

    void delete(UUID profileId);

    boolean exists(UUID profileId);
}
