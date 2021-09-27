package com.conferences.service.implementation;

import com.conferences.model.StorageProperties;
import com.conferences.service.abstraction.IStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * {@inheritDoc}
 */
@Log4j2
@Service
public class StorageService implements IStorageService {

    private final String location;

    @Autowired
    public StorageService(StorageProperties properties) {
        URL urlLoader = StorageService.class.getProtectionDomain().getCodeSource().getLocation();
        String loaderDir = urlLoader.getPath();
        this.location = loaderDir + properties.getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean store(MultipartFile file, String filename, String pathname) {
        Path path = init(location + pathname);
        log.info("Storing file");
        try {
            if (file.isEmpty()) {
                log.warn("File is empty");
                return false;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            log.error("Unable to store", exception);
            return false;
        }
        return true;
    }

    private Path init(String pathname) {
        Path path = Paths.get(pathname);
        try {
            Files.createDirectories(path);
        } catch (IOException exception) {
            log.error("Unable to initialize images directory", exception);
            return null;
        }
        return path;
    }
}
