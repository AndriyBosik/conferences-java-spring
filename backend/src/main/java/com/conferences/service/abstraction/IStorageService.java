package com.conferences.service.abstraction;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    boolean store(MultipartFile file, String filename, String path);
}
