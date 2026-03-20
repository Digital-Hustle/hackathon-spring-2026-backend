package com.hustle.rag_workspace_ms.utils;

import java.io.IOException;
import java.io.InputStream;

public interface FileParser {

    boolean supports(String contentType);

    String extractText(InputStream inputStream, String fileName) throws IOException;
}