package ru.core.profilems.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.core.profilems.constants.ErrorMessages;

@Builder(toBuilder = true)
public record SearchParametersRq(

        @NotNull(message = "query" + ErrorMessages.IS_REQUIRED)
        String query,

        boolean ignoreCase,

        Integer page,

        Integer size
) {
}
