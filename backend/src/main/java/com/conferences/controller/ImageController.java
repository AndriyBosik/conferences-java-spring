package com.conferences.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

@Log4j2
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping(value = "/{folder}/{filename}",  produces = "image/*")
    public Resource getImage(@PathVariable String folder, @PathVariable String filename) {
        ResourceLoader loader = new DefaultResourceLoader();
        log.info("Getting an image {}/{}", folder, filename);
        return loader.getResource("classpath:static/images/" + folder + "/" + filename);
    }
}
