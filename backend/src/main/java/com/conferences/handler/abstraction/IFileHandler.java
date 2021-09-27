package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to work with files
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IFileHandler {

    /**
     * <p>Generates new name for a file</p>
     * @param prefix prefix for a file name
     * @param filename old file name
     * @return generated filename
     */
    String generateNewFilename(String prefix, String filename);

    /**
     * <p>Adds timestamp to the end of filename</p>
     * @param prefix prefix of new filename
     * @param filename old file name
     * @return generated filename
     */
    String addTimestampToFilename(String prefix, String filename);
}
