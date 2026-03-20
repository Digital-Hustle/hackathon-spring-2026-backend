package ru.core.profilems.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import ru.core.profilems.constants.ErrorMessages;
import ru.core.profilems.constants.ProfileConstants;
import ru.core.profilems.validation.OnCreate;
import ru.core.profilems.validation.OnUpdate;

import java.time.LocalDate;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Profile data transfer object")
public record ProfileDto(

        @Schema(description = "Profile name", example = "Danil", type = "String")
        @NotNull(message = "name" + ErrorMessages.IS_REQUIRED, groups = {OnCreate.class, OnUpdate.class})
        @Length(min = ProfileConstants.MIN_NAME_LENGTH, max = ProfileConstants.MAX_NAME_LENGTH,
                message = "Name should be in range between {min} and {max} chars",
                groups = {OnCreate.class, OnUpdate.class})
        String name,

        @Schema(description = "Surname", example = "Chetvyrtov", type = "String")
        @NotNull(message = "surname" + ErrorMessages.IS_REQUIRED, groups = {OnCreate.class, OnUpdate.class})
        @Length(min = ProfileConstants.MIN_SURNAME_LENGTH, max = ProfileConstants.MAX_SURNAME_LENGTH,
                message = "Surname should be in range between {min} and {max} chars",
                groups = {OnCreate.class, OnUpdate.class})
        String surname,

        @Schema(description = "Birth date", example = "2020-05-03", type = "Integer")
        @NotNull(message = "dateOfBirth" + ErrorMessages.IS_REQUIRED, groups = {OnCreate.class, OnUpdate.class})
        LocalDate dateOfBirth
) {
}
