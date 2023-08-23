package com.beetech.api_intern.features.images;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String save(MultipartFile file, String directory);


    void deleteDirectory(String name);


    void createDirectory(String name);
}
