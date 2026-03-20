package ru.core.profilems.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record PhotoWithUrl(

        UUID id,

        String originalFileName,

        String url
) {
}
