package com.blog.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Integer> {

    Optional<ImageData> findByName(String name);

}
