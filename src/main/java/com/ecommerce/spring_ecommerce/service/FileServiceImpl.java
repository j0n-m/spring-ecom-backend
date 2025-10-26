package com.ecommerce.spring_ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void deleteImageIfExists(String path, String imageName) throws IOException {
        if (imageName == null || imageName.isEmpty()) {
            return;
        }
        Path dirPath = Paths.get(path);
        Path filePath = dirPath.resolve(imageName);

        Files.deleteIfExists(filePath);

    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Get file names of current file
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IOException("Invalid original file name");
        }
        //Generate a unique file name and Renaming file to avoid conflicts
        String uniqueId = UUID.randomUUID().toString();
        //Extract file extension of original file and append it to unique id
        String fileName = uniqueId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));

        //Create paths to directory that is system flexible
        Path dirPath = Paths.get(path);
        Path filePath = dirPath.resolve(fileName);

        //Check if path exists or create it
        File folder = new File(dirPath.toString());
        if (!folder.exists()) {
            Files.createDirectories(dirPath);
        }

        //Upload to server
        Files.copy(file.getInputStream(), filePath);

        //returning file name
        return fileName;
    }
}
