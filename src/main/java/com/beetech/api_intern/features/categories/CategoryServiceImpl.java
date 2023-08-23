package com.beetech.api_intern.features.categories;

import com.beetech.api_intern.common.exceptions.EntityNotFoundException;
import com.beetech.api_intern.features.categories.dto.CreateCategoryRequest;
import com.beetech.api_intern.features.categories.dto.UpdateCategoryRequest;
import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.images.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .build();
        categoryRepository.save(category);

        // create image directory name for category
        String directory = category.getImageDirectory();
        // save category image
        List<Image> images = imageService.saveListImage(createCategoryRequest.getImage(), directory);
        category.setImages(images);
        categoryRepository.save(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("category not found!"));
        category.setName(updateCategoryRequest.getName());
        if (updateCategoryRequest.getImage() != null && !updateCategoryRequest.getImage().isEmpty()) {
            List<Image> oldImages = category.getImages();
            // create image directory name for category
            String directory = category.getImageDirectory();
            // clear category images
            category.setImages(new ArrayList<>());
            categoryRepository.save(category);
            // delete old image
            imageService.deleteImages(oldImages, directory);

            List<Image> newImages = imageService.saveListImage(updateCategoryRequest.getImage(), directory);
            category.setImages(newImages);
        }
        categoryRepository.save(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(Long categoryId) {
        // find category by id or throw exception
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category not found"));

        // get category images
        List<Image> images = category.getImages();

        // delete category
        categoryRepository.deleteById(categoryId);

        // delete category images
        imageService.deleteImages(images, category.getImageDirectory());
    }
}
