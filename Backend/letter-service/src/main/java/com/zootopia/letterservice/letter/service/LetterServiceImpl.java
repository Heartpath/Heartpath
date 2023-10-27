package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.common.error.code.ErrorCode;
import com.zootopia.letterservice.common.error.exception.BadRequestException;
import com.zootopia.letterservice.common.s3.S3Uploader;
import com.zootopia.letterservice.letter.entity.LetterMongo;
import com.zootopia.letterservice.letter.repository.LetterJpaRepository;
import com.zootopia.letterservice.letter.repository.LetterMongoRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class LetterServiceImpl implements LetterService {
    private final LetterJpaRepository letterJpaRepository;
    private final LetterMongoRepository letterMongoRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public void createHandLetter(MultipartFile content, List<MultipartFile> files) {
        // content 파일
        if (content.isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_CONTENT);
        }

        String contentUrl = uploadFileToS3(content, "letter-hand-content");

        // letter file
        List<String> fileUrls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = uploadFileToS3(file, "letter-hand-files");
                fileUrls.add(fileUrl);
            }
        }

        LetterMongo letterMongo = LetterMongo.builder()
                .content(contentUrl)
                .files(fileUrls)
                .build();

        letterMongoRepository.save(letterMongo);
    }

    // 첨부된 파일이 이미지 파일인지 확인
    private boolean isImageFile(String filename) {
        String extenstion = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String[] allowedExtensions = {"png", "jpg", "jpeg", "heif", "hevc", "gif"};

        for (String allowedExtension : allowedExtensions) {
            if (extenstion.equals(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    private String uploadFileToS3(MultipartFile file, String s3Folder) {
        try {
            String fileName = file.getOriginalFilename();
            if (isImageFile(fileName)) {
                return s3Uploader.uploadFiles(file, s3Folder);
            } else {
                throw new BadRequestException(ErrorCode.INVALID_IMAGE_FORMAT);
            }
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.FAIL_UPLOAD_FILE);
        }
    }

    @Override
    @Transactional
    public void createTextLetter(String text, MultipartFile content, List<MultipartFile> files) {

    }
}
