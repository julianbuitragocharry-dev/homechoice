package com.homechoice.aws;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("images")
@AllArgsConstructor
public class ImageController {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile image) {
        try {
            return s3Service.uploadFile(image);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileUrl) {
        try {
            s3Service.deleteFile(fileUrl);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the file: " + e.getMessage());
        }
    }
}
