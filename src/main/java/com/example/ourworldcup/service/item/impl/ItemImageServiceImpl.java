package com.example.ourworldcup.service.item.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.service.item.ItemImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;


@RequiredArgsConstructor
@Service
public class ItemImageServiceImpl implements ItemImageService {

    private final AmazonS3Service amazonS3Service;
    @Override
    public String loadBase64Image(Item item) {
        return loadBase64Image(item.getItemImage());
    }

    @Override
    public String loadBase64Image(ItemImage itemImage) {
        String base64Image = "";
        try {
            System.out.println("itemImage.getUrl(): "+itemImage.getUrl());

            // 파일 이름에서 마지막 슬래시 뒤의 부분만 추출
            String[] parts = itemImage.getUrl().split("/");
            String extractedFileName = parts[parts.length - 1];
            String fileExtention = getFileExtension(itemImage.getUrl());

            S3Object s3Object = amazonS3Service.getFile(extractedFileName);
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            byte[] imageBytes = IOUtils.toByteArray(objectContent);
            base64Image = "data:image/"+fileExtention+";base64,"+Base64.getEncoder().encodeToString(imageBytes);
//            System.out.println(base64Image);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return base64Image;
    }

    protected String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
