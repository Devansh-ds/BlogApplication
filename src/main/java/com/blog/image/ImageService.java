package com.blog.image;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageDataRepository repo;
    private final ImageDataRepository imageDataRepository;
    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    public List<ImageData> uploadImage(List<MultipartFile> file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return new ArrayList<ImageData>();
        }

        List<ImageData> imageDataList = file.stream()
                .map(image -> {
                    try {
                        return ImageData.builder()
                                .name(image.getOriginalFilename())
                                .type(image.getContentType())
                                .imageData(ImageUtil.compressImage(image.getBytes()))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        return repo.saveAll(imageDataList);
    }

    @Transactional
    public ImageData getInfoByImageName(String name) {
        Optional<ImageData> imageData = repo.findByName(name);
        if (imageData.isEmpty()) {
            throw new EntityNotFoundException("Image not found with name : " + name);
        }
        return ImageData.builder()
                .name(imageData.get().getName())
                .type(imageData.get().getType())
                .imageData(ImageUtil.decompressImage(imageData.get().getImageData()))
                .build();
    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageData> imageData = repo.findByName(name);

        if (imageData.isEmpty()) {
            throw new EntityNotFoundException("Image not found with name : " + name);
        }

        byte[] image = ImageUtil.decompressImage(imageData.get().getImageData());
        return image;
    }


    public List<ImageData> updateImage(List<ImageData> oldImages, List<MultipartFile> file) throws IOException {

        log.info("Deleting old images : " + oldImages);

        oldImages.forEach(oldImage -> {
            Optional<ImageData> imageData = repo.findByName(oldImage.getName());
            if (imageData.isEmpty()) {
                throw new EntityNotFoundException("Image not found with name : " + oldImage.getName());
            }
            imageDataRepository.deleteById(oldImage.getId());
        });

        log.info("(Old images deleted successfully) Saving new images : {}", file);

        return uploadImage(file);
    }
}























