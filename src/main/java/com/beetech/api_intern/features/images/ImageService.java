package com.beetech.api_intern.features.images;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface ImageService {
    Image createImage(MultipartFile file, boolean isThumbnail);

    Image saveThumbnail(MultipartFile thumbnailImage);
    ArrayList<Image> saveListImage(List<MultipartFile> images);
}
