package com.conferences;

import com.conferences.model.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ConferencesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferencesApplication.class, args);
    }

}
