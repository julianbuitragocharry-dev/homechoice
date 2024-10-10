package com.homechoice.aws;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("homechoicebucket")
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            return "https://homechoicebucket.s3.us-east-2.amazonaws.com/" + fileName.replace(" ", "%20");
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public void deleteFile(String path) throws IOException {
        try {
            String fileName = path.replace("https://homechoicebucket.s3.us-east-2.amazonaws.com/", "");
            String key = fileName.replace("%20", " ");

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket("homechoicebucket")
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new IOException("Error deleting the file: " + e.getMessage(), e);
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadPaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = uploadFile(file);
            uploadPaths.add(path);
        }
        return uploadPaths;
    }

    public void deleteFiles(List<String> paths) throws IOException {
        for (String path : paths) {
            deleteFile(path);
        }
    }
}
