package com.example.spring_community.Image.service;

import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Image.domain.ImageEntity;
import com.example.spring_community.Image.dto.ImageDto;
import com.example.spring_community.Image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    private final String IMAGE_PATH = System.getProperty("user.dir") + "/images/";

    public String getFileType(MultipartFile multipartFile) {

        String contentType = multipartFile.getContentType();

        if(contentType != null) {
            MediaType mediaType = MediaType.parseMediaType(contentType);
            switch (mediaType.toString()) {
                case MediaType.IMAGE_JPEG_VALUE -> {
                    return ".jpeg";
                }
                case MediaType.IMAGE_PNG_VALUE -> {
                    return ".png";
                }
            }
        }

        return "";
    }


    public String setUniqueFileName(String fileType) {
        return UUID.randomUUID() + fileType;
    }

    public String getFilePath(MultipartFile multipartFile) {

        String type = getFileType(multipartFile);

        if(type.contentEquals("")) {
            throw new CustomException(ErrorCode.INVALID_FILE_FORMAT);
        }

        String fileName = setUniqueFileName(type);
        File file = new File(IMAGE_PATH + fileName);

        try {
            multipartFile.transferTo(file);
        }
        catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_FILE_FORMAT);
        }

        return fileName;
    }

    @Transactional
    public ImageEntity uploadProfileImage(MultipartFile multipartFile) {
        String fileName = getFilePath(multipartFile);
        ImageEntity imageEntity = ImageEntity.builder()
                .fileName(fileName).build();
        return imageRepository.save(imageEntity);
    }

}
