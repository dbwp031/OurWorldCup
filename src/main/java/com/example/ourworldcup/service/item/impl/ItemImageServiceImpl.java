package com.example.ourworldcup.service.item.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.repository.item.ItemRepository;
import com.example.ourworldcup.service.item.ItemImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;


@RequiredArgsConstructor
@Service
public class ItemImageServiceImpl implements ItemImageService {

}
