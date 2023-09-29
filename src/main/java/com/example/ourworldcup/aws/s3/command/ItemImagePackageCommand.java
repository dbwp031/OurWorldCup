package com.example.ourworldcup.aws.s3.command;

import com.example.ourworldcup.aws.s3.metadata.ItemImagePackageMetadata;

public class ItemImagePackageCommand implements  AmazonS3PackageCommand{
    private ItemImagePackageMetadata itemImagePackageMetadata;

    public ItemImagePackageCommand(ItemImagePackageMetadata itemImagePackageMetadata) {
        this.itemImagePackageMetadata = itemImagePackageMetadata;
    }
    @Override
    public String getFolder() {
        return getFolderInternal(itemImagePackageMetadata);
    }

    private String getFolderInternal(ItemImagePackageMetadata itemImagePackageMetadata) {
        return "";
    }
}
