package com.revplay.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
public class FileUploadUtil {

    private static final String UPLOAD_DIR = "uploads/";

    public String uploadFile(MultipartFile file, String folderName) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Create folder if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR + folderName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);

            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            Path filePath = uploadPath.resolve(uniqueFileName);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return file path to store in DB
            return "/" + UPLOAD_DIR + folderName + "/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    private String getFileExtension(String fileName) {

        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf("."));
    }
}