package com.example.ourworldcup.aws.s3.metadata;

import com.example.ourworldcup.aws.s3.command.AmazonS3PackageCommand;

public abstract class FilePackageMetadata {
    private String uuid;

    public FilePackageMetadata(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public abstract AmazonS3PackageCommand createCommand();

}
