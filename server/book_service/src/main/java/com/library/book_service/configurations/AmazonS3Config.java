package com.library.book_service.configurations;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmazonS3Config {
    @Value("${amazon.region}")
    String region;
    @Value("${amazon.accessKey}")
    String accessKey;
    @Value("${amazon.secretKey}")
    String secretKey;


    @Bean(name = "amazonS3ClientConfig")
    public S3Client amazonS3Client(){
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

}
