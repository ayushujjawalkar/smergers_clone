package com.abhi.smergersclone.service;
import com.abhi.smergersclone.exception.FileStorageException;
import com.abhi.smergersclone.exception.FileStorageProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

//@Service
//public class FileStorageService {
//
//    private final Path rootLocation;
//
//    public FileStorageService() {
//        this.rootLocation = Paths.get("uploads");
//        try {
//            Files.createDirectories(rootLocation);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize storage location", e);
//        }
//    }
//
//    public String storeFile(String subDirectory, MultipartFile file) {
//        try {
//            String originalFilename = file.getOriginalFilename();
//            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String newFilename = UUID.randomUUID() + fileExtension;
//
//            Path targetLocation = this.rootLocation.resolve(subDirectory).resolve(newFilename);
//            Files.createDirectories(targetLocation.getParent());
//
//            Files.copy(file.getInputStream(), targetLocation);
//            return targetLocation.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
//        }
//    }
//
//    public void deleteFile(String filePath) {
//        try {
//            Path path = Paths.get(filePath);
//            Files.deleteIfExists(path);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to delete file " + filePath, e);
//        }
//    }
//}
@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create upload directory", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check for invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Invalid file name: " + fileName);
            }

            // Copy file to target location
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName, ex);
        }
    }
}
