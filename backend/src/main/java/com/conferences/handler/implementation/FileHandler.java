package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
@Log4j2
@Component
public class FileHandler implements IFileHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateNewFilename(String prefix, String filename) {
        log.info("Generating new filename");
        String extension = getFileExtension(filename);
        return prefix + (extension.length() > 0 ? ("." + extension) : "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addTimestampToFilename(String prefix, String filename) {
        log.info("Adding timestamp to filename");
        String timestamp = retrieveDigits(LocalDateTime.now().toString());
        return prefix + timestamp + "." + getFileExtension(filename);
    }

    /**
     * <p>Parses file name to extract file extension</p>
     * @param filename name of file to be parsed
     * @return file extension
     */
    private String getFileExtension(String filename) {
        if (filename.length() == 0) {
            return "";
        }
        String[] parts = filename.split("\\.");
        if (parts.length == 1) {
            return "";
        }
        return parts[parts.length - 1];
    }

    /**
     * <p>Retrieves only digits from a string</p>
     * @param timestamp value to retrive digits from
     * @return string which contains only digits
     */
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
