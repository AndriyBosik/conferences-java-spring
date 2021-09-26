package com.conferences.handler;

import com.conferences.handler.implementation.FileHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private static FileHandler fileHandler;

    @BeforeAll
    private static void beforeAll() {
        fileHandler = new FileHandler();
    }

    @Test
    public void shouldGenerateNewFilename() {
        String result = fileHandler.generateNewFilename("testFile", "avatar.png");

        assertEquals("testFile.png", result);
    }

    @Test
    public void shouldNotAddExtensionToFilename() {
        String result = fileHandler.generateNewFilename("filename", "inputFile");

        assertEquals("filename", result);
    }

    @Test
    public void shouldAddTimestampToFilename() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        String result = fileHandler.addTimestampToFilename("filename", "inputFile.txt");

        assertTrue(result.startsWith("filename" + ldt.getYear()));
    }
}
