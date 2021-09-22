package com.conferences.handler.abstraction;

public interface IFileHandler {

    String generateNewFilename(String prefix, String filename);

    String addTimestampToFilename(String prefix, String filename);
}
