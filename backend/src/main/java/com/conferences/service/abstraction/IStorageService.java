package com.conferences.service.abstraction;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *     Defines methods to work with file storage
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IStorageService {

    /**
     * <p>Stores file in storage</p>
     * @param file file to be stored
     * @param filename new name of file
     * @param path path to save file in
     * @return true if file was successfully stored, false otherwise
     */
    boolean store(MultipartFile file, String filename, String path);
}
