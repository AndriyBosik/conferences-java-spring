package com.conferences.controller;

import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.IStorageService;
import com.conferences.service.abstraction.IUserService;
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
    private final IUserService userService;

    public FileController(IStorageService storageService, ISecurityService securityService, IFileHandler fileHandler, IUserService userService) {
        this.storageService = storageService;
        this.securityService = securityService;
        this.fileHandler = fileHandler;
        this.userService = userService;
    }

    @PostMapping("/save-avatar")
    public String saveAvatar(@RequestParam("file") MultipartFile file) {
        String filename = fileHandler.generateNewFilename(securityService.getUserLogin(), file.getOriginalFilename());
        if (storageService.store(file, filename, "/avatars")) {
            return userService.updateUserImagePath(filename);
        }
        return "";
    }
}
