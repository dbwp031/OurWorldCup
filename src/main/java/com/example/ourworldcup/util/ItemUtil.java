package com.example.ourworldcup.util;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class ItemUtil {
}
