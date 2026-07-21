package com.company.taskportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/{folder}/{fileName:.+}")
    public ResponseEntity<Resource> getFile(
            @PathVariable String folder,
            @PathVariable String fileName)
            throws MalformedURLException {

        Path path = Paths.get(uploadDir)
                .resolve(folder)
                .resolve(fileName);

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = "application/octet-stream";

        if (fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")) {

            contentType = MediaType.IMAGE_JPEG_VALUE;

        } else if (fileName.endsWith(".png")) {

            contentType = MediaType.IMAGE_PNG_VALUE;

        } else if (fileName.endsWith(".pdf")) {

            contentType = MediaType.APPLICATION_PDF_VALUE;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}