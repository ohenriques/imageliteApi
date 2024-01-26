package com.image.liteApi.controller;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ImageDTO {

    private String url;
    private String nome;
    private String extension;
    private Long size;
    private LocalDate uploadDate;
}
