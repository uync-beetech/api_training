package com.beetech.api_intern.features.images;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final FileStorageService storageService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Image createImage(MultipartFile file, boolean isThumbnail) {
        String imagePath = storageService.save(file);
        return Image.builder().path(imagePath).name(file.getOriginalFilename()).thumbnail(isThumbnail).build();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Image saveThumbnail(MultipartFile thumbnailImage) {
        return imageRepository.save(createImage(thumbnailImage, true));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ArrayList<Image> saveListImage(List<MultipartFile> images) {
        List<Image> imageList = images.stream().map(image -> createImage(image, false)).toList();
        imageRepository.saveAll(imageList);
        return new ArrayList<>(imageList);
    }
}
