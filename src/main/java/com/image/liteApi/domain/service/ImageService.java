package com.image.liteApi.domain.service;

import com.image.liteApi.domain.entity.Image;

import java.util.Optional;


public interface ImageService {

    Image save(Image save);

    Optional<Image> getById(String id);

    void deleteAll();
}
