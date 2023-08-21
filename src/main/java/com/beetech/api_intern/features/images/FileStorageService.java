package com.beetech.api_intern.features.images;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    void init();

    String save(MultipartFile file);

    void deleteListFile(List<String> fileNames);
    void deleteFile(String fileName);
}
