package com.conferences.controller;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping(value = "/avatars/{filename}",  produces = "image/*")
    public Resource getAvatar(@PathVariable String filename) {
        ResourceLoader loader = new DefaultResourceLoader();
        return loader.getResource("classpath:static/images/avatars/" + filename);
    }
}
