package com.conferences.controller;

import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.abstraction.IStorageService;
import com.conferences.service.abstraction.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *     Controller which contains routes to handle file processing
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
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

    /**
     * <p>Saves user avatar to server</p>
     * @param file file to be saved
     * @return new image filename
     */
    @PostMapping("/save-avatar")
    public String saveAvatar(@RequestParam("file") MultipartFile file) {
        log.info("Saving user avatar");
        String filename = fileHandler.generateNewFilename(securityService.getUserLogin(), file.getOriginalFilename());
        if (storageService.store(file, filename, "/avatars")) {
            log.info("Updating user image path");
            return userService.updateUserImagePath(filename);
        }
        log.warn("Avatar is not saved");
        return "";
    }
}
