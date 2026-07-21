package com.company.taskportal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file, String folder) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {

            Path directory = Paths.get(uploadDir, folder);

            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String originalName = StringUtils.cleanPath(file.getOriginalFilename());

            String extension = "";

            int index = originalName.lastIndexOf(".");

            if (index > 0) {
                extension = originalName.substring(index);
            }

            String fileName = UUID.randomUUID() + extension;

            Path targetLocation = directory.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return folder + "/" + fileName;

        } catch (IOException ex) {

            log.error("Unable to upload file", ex);

            throw new RuntimeException("Could not upload file.");
        }
    }

    @Override
    public void deleteFile(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return;
        }

        try {

            Path path = Paths.get(uploadDir).resolve(filePath);

            Files.deleteIfExists(path);

        } catch (IOException ex) {

            log.error("Unable to delete file {}", filePath, ex);
        }
    }
}