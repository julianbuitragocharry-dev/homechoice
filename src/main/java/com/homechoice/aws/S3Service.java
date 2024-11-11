package com.homechoice.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for handling file operations with AWS S3.
 */
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    /**
     * AWS S3 Bucket name configured in application properties.
     */
    @Value("${cloud-storage.s3.bucket-name}")
    private String bucketName;

    /**
     * Uploads a single file to the configured S3 bucket.
     *
     * @param file the {@link MultipartFile} to be uploaded.
     * @return the URL of the uploaded file.
     * @throws IOException if an error occurs while uploading the file.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            return "https://" + bucketName + ".s3.us-east-2.amazonaws.com/" + fileName.replace(" ", "%20");
        } catch (Exception e) {
            throw new IOException("Error uploading file: " + e.getMessage());
        }
    }

    /**
     * Deletes a file from the configured S3 bucket based on the file URL.
     *
     * @param path the URL of the file to be deleted.
     * @throws IOException if an error occurs while deleting the file.
     */
    public void deleteFile(String path) throws IOException {
        try {
            String fileName = path.replace("https://homechoicebucket.s3.us-east-2.amazonaws.com/", "");
            String key = fileName.replace("%20", " ");

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new IOException("Error deleting file: " + e.getMessage(), e);
        }
    }

    /**
     * Uploads multiple files to the configured S3 bucket.
     *
     * @param files a list of {@link MultipartFile} objects to be uploaded.
     * @return a list of URLs of the uploaded files.
     * @throws IOException if an error occurs while uploading any of the files.
     */
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadPaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = uploadFile(file);
            uploadPaths.add(path);
        }
        return uploadPaths;
    }

    /**
     * Deletes multiple files from the configured S3 bucket.
     *
     * @param paths a list of URLs of the files to be deleted.
     * @throws IOException if an error occurs while deleting any of the files.
     */
    public void deleteFiles(List<String> paths) throws IOException {
        for (String path : paths) {
            deleteFile(path);
        }
    }
}
