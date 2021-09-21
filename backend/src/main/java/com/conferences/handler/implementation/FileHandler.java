package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import org.springframework.stereotype.Component;

@Component
public class FileHandler implements IFileHandler {

    @Override
    public String generateNewFilename(String prefix, String filename) {

        return prefix + "." + getFileExtension(filename);
    }

    private String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        if (parts.length == 0) {
            return "";
        }
        return parts[parts.length - 1];
    }
}
