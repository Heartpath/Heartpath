package com.zootopia.userservice.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String getUserProfilePathByMemberID(String memberID) {
        return "user-profile-image/".concat(memberID);
    }

    public String saveUserProfileImage(MultipartFile multipartFile, String memberID) {

        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = getUserProfilePathByMemberID(memberID);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            log.error("Cannot Upload '{}' Image : {}", originalFileName, e.toString());
            throw new RuntimeException("cannot upload image");
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    public void deleteImage(String memberID) {
        String fileName = getUserProfilePathByMemberID(memberID);
        amazonS3Client.deleteObject(bucketName, fileName);
    }
}