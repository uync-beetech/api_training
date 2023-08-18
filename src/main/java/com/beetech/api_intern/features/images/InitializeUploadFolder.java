package com.beetech.api_intern.features.images;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializeUploadFolder implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeUploadFolder.class);
    private final FileStorageService fileStorageService;

    @Override
    public void run(String... args) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("init upload folder");
        }
        fileStorageService.init();
    }
}
