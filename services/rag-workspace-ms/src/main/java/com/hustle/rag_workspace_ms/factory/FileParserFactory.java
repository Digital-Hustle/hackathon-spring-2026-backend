package com.hustle.rag_workspace_ms.factory;

import com.hustle.rag_workspace_ms.utils.FileParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class FileParserFactory {

    private final List<FileParser> parsers;

    public FileParser getParser(String contentType) {
        return parsers.stream()
                .filter(p -> p.supports(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No parser for content type: " + contentType));
    }
}