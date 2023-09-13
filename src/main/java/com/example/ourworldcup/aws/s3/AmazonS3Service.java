package com.example.ourworldcup.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.ourworldcup.aws.s3.command.AmazonS3PackageCommand;
import com.example.ourworldcup.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AmazonS3Service implements FileService{
    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), fileName, inputStream, objectMetadata));
    }

    @Override
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(amazonConfig.getBucket(), fileName));
    }

    @Override
    public String getFileUrl(String path) {
        return amazonS3.getUrl(amazonConfig.getBucket(), path).toString();
    }

    @Override
    public String getFileFolder(AmazonS3PackageCommand command) {
        return command.getFolder();
    }

    public S3Object getFile(String path) {
        return amazonS3.getObject(amazonConfig.getBucket(), path);
    }
}
