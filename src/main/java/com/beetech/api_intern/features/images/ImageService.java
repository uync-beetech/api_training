package com.beetech.api_intern.features.images;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface ImageService {
    Image createImage(MultipartFile file, String sku, boolean isThumbnail);

    Image saveThumbnail(MultipartFile thumbnailImage, String directory);
    ArrayList<Image> saveListImage(List<MultipartFile> images, String directory);
    void deleteImages(List<Image> images, String directory);
}
