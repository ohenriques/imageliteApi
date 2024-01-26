package com.image.liteApi.controller;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;


    @PostMapping
    public ResponseEntity save(@RequestParam("file") MultipartFile file,
                               @RequestParam("name") String name,
                               @RequestParam("") List<String> tags) throws IOException {

        log.debug("Imagem Recebida: Name: {}, sieze: {}", file.getOriginalFilename(), file.getSize());

        MediaType.valueOf(file.getContentType());

        Image image = mapper.mapToImage(file, name, tags);
        Image imageSaved = service.save(image);
        URI imageUrlCreated = buildImageURL(imageSaved);

        return ResponseEntity.created(imageUrlCreated).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImages(@PathVariable String id) {
        var possibleImage = service.getById(id);
        if (possibleImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Image image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());

        headers.setContentDispositionFormData("inline; filename= \"" + image.getFileName() + "\"", image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete() {

        service.deleteAll();

        return ResponseEntity.noContent().build();
    }

    private URI buildImageURL(Image image) {
        String imagePath = "/" + image.getId();

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(imagePath)
                .build()
                .toUri();
    }
}
