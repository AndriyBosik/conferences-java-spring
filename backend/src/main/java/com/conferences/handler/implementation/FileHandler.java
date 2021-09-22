package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FileHandler implements IFileHandler {

    @Override
    public String generateNewFilename(String prefix, String filename) {
        return prefix + "." + getFileExtension(filename);
    }

    @Override
    public String addTimestampToFilename(String prefix, String filename) {
        String timestamp = retrieveDigits(LocalDateTime.now().toString());
        return prefix + timestamp + "." + getFileExtension(filename);
    }

    private String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        if (parts.length == 0) {
            return "";
        }
        return parts[parts.length - 1];
    }

    private String retrieveDigits(String timestamp) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < timestamp.length(); i++) {
            char symbol = timestamp.charAt(i);
            if (symbol >= '0' && symbol <= '9') {
                result.append(symbol);
            }
        }
        return result.toString();
    }
}
