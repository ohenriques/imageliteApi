package com.image.liteApi.domain.repository;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.enums.ImageExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImageRespository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    // TODO: 25/01/2024 JPASpecification...

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {

        if (extension != null) {
            //add in query
        }
        return findAll();
    }
}
