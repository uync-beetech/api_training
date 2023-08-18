package com.beetech.api_intern.features.images;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();
    String save(MultipartFile file);
}
