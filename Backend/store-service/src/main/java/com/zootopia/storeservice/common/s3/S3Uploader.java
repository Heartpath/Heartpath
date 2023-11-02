package com.zootopia.storeservice.common.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.zootopia.storeservice.common.error.code.ErrorCode;
import com.zootopia.storeservice.common.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
        // MultipartFile → File 변환할 수 없으면 에러
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> { return new IllegalArgumentException("error : MultipartFile → File convert fail");});
        return upload(uploadFile, dirName);
    }

    public boolean deleteFiles(String fileName) throws IOException{
        try{
            amazonS3Client.deleteObject(bucket, fileName);
        } catch (SdkClientException e) {
            throw new BadRequestException(ErrorCode.FAIL_DELETE_FILE);
        }
        return true;
    }

    public String upload(File uploadFile, String filePath) {
        // S3에 저장된 파일 이름 생성(중복x) → 다른 사용자가 같은 이름의 파일을 업로드하여도 다른 파일 이름을 가짐.
        String fileName = filePath + "/" + UUID.randomUUID() + "." + uploadFile.getName();
        String uploadImageUrl = putS3(fileName, uploadFile);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(String fileName, File uploadFile) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.error("File delete fail");
    }


    // MultipartFile → File 변환, 로컬에 파일 업로드 하여 파일 전처리 과정 필요시 수행
    // 성공 → File 객체, 실패 → 빈 Optional 객체
    private Optional<File> convert(MultipartFile file) throws IOException {
         File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
         if (convertFile.createNewFile()) {
             try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                 fos.write(file.getBytes());
             }
             return Optional.of(convertFile);
         }
         return Optional.empty();
    }
}
