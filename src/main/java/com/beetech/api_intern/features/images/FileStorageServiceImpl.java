package com.beetech.api_intern.features.images;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final Path root = Paths.get("./uploads");

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
            throw new ServerErrorException("Could not initialize folder for upload!", e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            String originalName = Objects.requireNonNull(file.getOriginalFilename());
            int dotIndex = originalName.lastIndexOf(".");
            String newFileName;
            if (dotIndex >= 0) {
                String fileName = originalName.substring(0, dotIndex);
                String fileExtension = originalName.substring(dotIndex);
                newFileName = fileName + "_" + System.currentTimeMillis() + fileExtension;
            } else {
                newFileName = originalName + "_" + System.currentTimeMillis();
            }
            Files.copy(file.getInputStream(), this.root.resolve(newFileName));
            return newFileName;
        } catch (IOException e) {
            throw new ServerErrorException("Server error: ", e);
        }
    }

    @Override
    public void deleteListFile(List<String> fileNames) {
        fileNames.forEach(this::deleteFile);
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Delete image: {}", fileName);
            }
            Path path = root.resolve(fileName);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new ServerErrorException("Server error: ", e);
        }
    }
}
