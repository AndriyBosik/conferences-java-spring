package com.conferences.service.implementation;

import com.conferences.config.StorageProperties;
import com.conferences.service.abstraction.IStorageService;
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

@Service
public class StorageService implements IStorageService {

    private final String location;

    @Autowired
    public StorageService(StorageProperties properties) {
        URL urlLoader = StorageService.class.getProtectionDomain().getCodeSource().getLocation();
        String loaderDir = urlLoader.getPath();
        
        this.location = loaderDir + properties.getLocation();
    }

    private Path init(String pathname) {
        Path path = Paths.get(pathname);
        try {
            Files.createDirectories(path);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        return path;
    }

    @Override
    public boolean store(MultipartFile file, String filename, String pathname) {
        Path path = init(location + pathname);
        try {
            if (file.isEmpty()) {
                return false;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
