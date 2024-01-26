package com.image.liteApi.domain.repository;

import com.image.liteApi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRespository extends JpaRepository<Image, String> {
}
