package ru.ci_trainee.authms.mapper;

import org.mapstruct.Mapper;
import ru.ci_trainee.authms.dto.request.UserRegisterRs;
import ru.ci_trainee.authms.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterRs toDto(User user);

    User toEntity(UserRegisterRs userRegisterRs);
}