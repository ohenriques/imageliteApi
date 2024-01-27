package com.image.liteApi.controller;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.enums.ImageExtension;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

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

    @GetMapping("all")
    public ResponseEntity<Image[]> getAll() {

        List<Image> imageList = service.getAllImages();

        Image[] images = imageList.toArray(new Image[imageList.size()]);

        return ResponseEntity.ok(images);
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

    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
            @RequestParam(value = "query", required = false) String query
    ) {
        var result = service.search(ImageExtension.ofName(extension), query);

        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return mapper.imageToDTO(image, url.toString());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }

    @DeleteMapping
    public ResponseEntity delete() {

        service.deleteAll();

        return ResponseEntity.noContent().build();
    }

    private URI buildImageURL(Image image) {
        String imagePath = "/" + image.getId();

        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(imagePath)
                .build()
                .toUri();
    }
}
