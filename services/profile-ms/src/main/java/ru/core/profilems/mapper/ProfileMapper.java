package ru.core.profilems.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.core.profilems.config.BaseMapperConfig;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.model.entity.Profile;

@Mapper(config = BaseMapperConfig.class)
public interface ProfileMapper {

    ProfileDto convert(Profile source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Profile convert(ProfileDto source);
}
