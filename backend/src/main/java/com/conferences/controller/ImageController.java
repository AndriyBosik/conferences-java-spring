package com.conferences.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

/**
 * <p>
 *     Controller which contains routes to handle images requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
@RestController
@RequestMapping("/api/images")
public class ImageController {

    /**
     * <p>Returns image by path</p>
     * @param folder folder with image
     * @param filename image name
     * @return requested image
     */
    @GetMapping(value = "/{folder}/{filename}",  produces = "image/*")
    public Resource getImage(@PathVariable String folder, @PathVariable String filename) {
        ResourceLoader loader = new DefaultResourceLoader();
        log.info("Getting an image {}/{}", folder, filename);
        return loader.getResource("classpath:static/images/" + folder + "/" + filename);
    }
}
