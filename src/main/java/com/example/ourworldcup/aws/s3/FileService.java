package com.example.ourworldcup.aws.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ourworldcup.aws.s3.command.AmazonS3PackageCommand;

import java.io.InputStream;

public interface FileService {
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    void deleteFile(String fileName);

    String getFileUrl(String path);

    String getFileFolder(AmazonS3PackageCommand amazonS3PackageCommand);

}
