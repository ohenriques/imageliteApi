package com.image.liteApi.controller;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.enums.ImageExtension;
import com.image.liteApi.domain.repository.ImageRespository;
import com.image.liteApi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class imageServiceImpl implements ImageService {

    private final ImageRespository respository;

    @Override
    public Image save(Image image) {
        return respository.save(image);
    }

    @Override
    public Optional<Image> getById(String id) {
        return respository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return respository.findByExtensionAndNameOrTagsLike(extension, query);
    }

    @Override
    public List<Image> getAllImages() {
        return respository.findAll();
    }

    @Override
    public void deleteAll() {
        respository.deleteAll();
    }
}
