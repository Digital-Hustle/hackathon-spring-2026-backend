package com.hustle.rag_workspace_ms.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class TxtParser implements FileParser {
    @Override
    public boolean supports(String contentType) {
        return contentType != null && (contentType.equals("text/plain") ||
                contentType.equals("text/plain; charset=utf-8"));
    }

    @Override
    public String extractText(InputStream inputStream, String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}