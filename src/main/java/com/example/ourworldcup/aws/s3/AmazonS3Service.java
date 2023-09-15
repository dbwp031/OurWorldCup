package com.example.ourworldcup.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.ourworldcup.aws.s3.command.AmazonS3PackageCommand;
import com.example.ourworldcup.config.AmazonConfig;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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

    public String loadBase64Image(Item item) throws Exception{
        return loadBase64Image(item.getItemImage());
    }
    @Override
    public String loadBase64Image(ItemImage itemImage) throws IOException {
        String base64Image = "";
        try {
            System.out.println("itemImage.getUrl(): "+itemImage.getUrl());

            // 파일 이름에서 마지막 슬래시 뒤의 부분만 추출
            String[] parts = itemImage.getUrl().split("/");
            String extractedFileName = parts[parts.length - 1];
            String fileExtention = getFileExtension(itemImage.getUrl());

            S3Object s3Object = this.getFile(extractedFileName);
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            byte[] imageBytes = IOUtils.toByteArray(objectContent);
            base64Image = "data:image/"+fileExtention+";base64,"+ Base64.getEncoder().encodeToString(imageBytes);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return base64Image;
    }
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
