package com.beetech.api_intern.features.images;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The type File storage service.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final Path root = Paths.get("./uploads");

    @Override
    public String save(MultipartFile file, String directory) {
        try {
            createDirectory(directory);
            String originalName = Objects.requireNonNull(file.getOriginalFilename());
            String newFileName;
            // get file name
            String fileName = FilenameUtils.getBaseName(originalName);
            // get file extension
            String fileExtension = FilenameUtils.getExtension(originalName);

            // append current time milliseconds
            newFileName = fileName + System.currentTimeMillis();

            // check this file has extension
            if (!fileExtension.isEmpty()) {
                newFileName = newFileName + "." + fileExtension;
            }

            Files.copy(file.getInputStream(), this.root.resolve(directory).resolve(newFileName));
            return directory + "/" + newFileName;
        } catch (IOException e) {
            throw new ServerErrorException("Server error: ", e);
        }
    }

    @Override
    public void deleteDirectory(String name) {
        try {
            File directory = root.resolve(name).toFile();
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            LOGGER.error("Delete directory error: {}", e.getMessage());
        }
    }

    @Override
    public void createDirectory(String name) {
        File directory = root.resolve(name).toFile();
        if (directory.mkdir()) {
            LOGGER.info("Create directory {} success", name);
        }
    }
}
