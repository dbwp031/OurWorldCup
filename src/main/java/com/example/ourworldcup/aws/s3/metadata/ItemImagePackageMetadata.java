package com.example.ourworldcup.aws.s3.metadata;

import com.example.ourworldcup.aws.s3.command.AmazonS3PackageCommand;
import com.example.ourworldcup.aws.s3.command.ItemImagePackageCommand;
import com.example.ourworldcup.domain.Uuid;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemImagePackageMetadata extends FilePackageMetadata {
    private final String fileName;
    private final Uuid uuidEntity;


    @Builder
    public ItemImagePackageMetadata(String uuid, String fileName, Uuid uuidEntity) {
        super(uuid);
        this.fileName = fileName;
        this.uuidEntity = uuidEntity;
    }
    @Override
    public AmazonS3PackageCommand createCommand() {
        return new ItemImagePackageCommand(this);
    }
}
