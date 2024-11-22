package com.homechoice.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for AWS S3 client.
 * Sets up an S3Client bean with the necessary credentials and region.
 *
 * @since 1.0
 */
@Configuration
public class S3Config {

    /**
     * AWS Access Key for authentication.
     * This value is injected from the application properties.
     */
    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    /**
     * AWS Secret Key for authentication.
     * This value is injected from the application properties.
     */
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    /**
     * AWS region for the S3 client.
     * This value is injected from the application properties.
     */
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    /**
     * Creates and configures an S3Client bean.
     * The S3Client is configured with the specified region and credentials.
     *
     * @return Configured {@link S3Client} instance.
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials()))
                .build();
    }

    /**
     * Builds AWS credentials using access and secret keys.
     * The credentials are used to authenticate requests to AWS services.
     *
     * @return Configured {@link AwsBasicCredentials} instance with configured access and secret keys.
     */
    private AwsBasicCredentials awsCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }
}
