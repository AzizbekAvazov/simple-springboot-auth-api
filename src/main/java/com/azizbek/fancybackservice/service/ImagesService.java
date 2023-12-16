package com.azizbek.fancybackservice.service;

import com.azizbek.fancybackservice.entity.Images;
import com.azizbek.fancybackservice.repository.ImagesRepo;
import org.springframework.stereotype.Service;

/**
 * Creator: Azizbek Avazov
 * Date: 13.08.2022
 * Time: 10:15
 */
@Service
public class ImagesService {
    private final ImagesRepo imagesRepo;

    public ImagesService(ImagesRepo imagesRepo) {
        this.imagesRepo = imagesRepo;
    }

    public Images saveImage(Images image) {
        return imagesRepo.save(image);
    }
}
