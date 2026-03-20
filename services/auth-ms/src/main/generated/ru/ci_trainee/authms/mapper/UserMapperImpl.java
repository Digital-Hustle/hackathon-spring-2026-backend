package ru.ci_trainee.authms.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.ci_trainee.authms.dto.request.UserRegisterRs;
import ru.ci_trainee.authms.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T20:10:07+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRegisterRs toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterRs userRegisterRs = new UserRegisterRs();

        userRegisterRs.setId( user.getId() );
        userRegisterRs.setUsername( user.getUsername() );
        userRegisterRs.setIsActive( user.getIsActive() );
        userRegisterRs.setPassword( user.getPassword() );

        return userRegisterRs;
    }

    @Override
    public User toEntity(UserRegisterRs userRegisterRs) {
        if ( userRegisterRs == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userRegisterRs.getId() );
        user.username( userRegisterRs.getUsername() );
        user.password( userRegisterRs.getPassword() );
        user.isActive( userRegisterRs.getIsActive() );

        return user.build();
    }
}
