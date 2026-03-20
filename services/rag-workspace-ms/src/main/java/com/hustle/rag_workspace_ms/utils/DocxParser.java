package com.hustle.rag_workspace_ms.utils;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DocxParser implements FileParser {

    @Override
    public boolean supports(String contentType) {
        return contentType != null && (
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        );
    }

    @Override
    public String extractText(InputStream inputStream, String fileName) throws IOException {
        if (fileName.endsWith(".docx")) {
            try (XWPFDocument document = new XWPFDocument(inputStream);
                 XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
                return extractor.getText();
            }
        }
//        else if (fileName.endsWith(".doc")) {
//            try (HWPFDocument document = new HWPFDocument(inputStream);
//                 WordExtractor extractor = new WordExtractor(document)) {
//                return extractor.getText();
//            }
//        }
        throw new UnsupportedOperationException("Unsupported Word file: " + fileName);
    }
}