package com.conferences.controller;

import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.IStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final IStorageService storageService;
    private final ISecurityService securityService;
    private final IFileHandler fileHandler;

    public FileController(IStorageService storageService, ISecurityService securityService, IFileHandler fileHandler) {
        this.storageService = storageService;
        this.securityService = securityService;
        this.fileHandler = fileHandler;
    }

    @PostMapping("/save-avatar")
    public String saveAvatar(@RequestParam("file") MultipartFile file) {
        String filename = fileHandler.generateNewFilename(securityService.getUserLogin(), file.getOriginalFilename());
        storageService.store(file, filename, "/avatars");
        return filename;
    }
}
