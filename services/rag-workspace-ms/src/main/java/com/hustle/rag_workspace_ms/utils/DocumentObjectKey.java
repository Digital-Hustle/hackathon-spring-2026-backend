package com.hustle.rag_workspace_ms.utils;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;

import java.util.Locale;

public final class DocumentObjectKey {

    private DocumentObjectKey() {
    }

    public static String from(DocumentMeta documentMeta) {
        return "%s.%s".formatted(
                documentMeta.getId(),
                documentMeta.getExtension().name().toLowerCase(Locale.ROOT)
        );
    }
}
