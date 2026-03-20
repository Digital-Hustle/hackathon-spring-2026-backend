package com.hustle.rag_workspace_ms.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfParser implements FileParser {

    @Override
    public boolean supports(String contentType) {
        return contentType != null && contentType.equals("application/pdf");
    }

    @Override
    public String extractText(InputStream inputStream, String fileName) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}