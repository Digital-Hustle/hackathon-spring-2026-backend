package ru.core.profilems.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.model.entity.Profile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T20:19:59+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileDto convert(Profile source) {
        if ( source == null ) {
            return null;
        }

        ProfileDto.ProfileDtoBuilder profileDto = ProfileDto.builder();

        profileDto.name( source.getName() );
        profileDto.surname( source.getSurname() );
        profileDto.dateOfBirth( source.getDateOfBirth() );

        return profileDto.build();
    }

    @Override
    public Profile convert(ProfileDto source) {
        if ( source == null ) {
            return null;
        }

        Profile.ProfileBuilder profile = Profile.builder();

        profile.name( source.name() );
        profile.surname( source.surname() );
        profile.dateOfBirth( source.dateOfBirth() );

        return profile.build();
    }
}
