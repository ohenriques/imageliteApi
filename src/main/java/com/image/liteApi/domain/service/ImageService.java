package com.image.liteApi.domain.service;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;


public interface ImageService {

    Image save(Image save);

    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);

    List<Image> getAllImages();

    void deleteAll();
}
