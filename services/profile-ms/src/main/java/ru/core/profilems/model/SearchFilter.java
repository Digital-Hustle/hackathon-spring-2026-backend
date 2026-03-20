package ru.core.profilems.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchFilter(

        String query,

        boolean ignoreCase,

        Integer page,

        Integer size
) {
}
