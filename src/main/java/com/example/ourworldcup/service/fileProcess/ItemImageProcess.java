package com.example.ourworldcup.service.fileProcess;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.aws.s3.metadata.ItemImagePackageMetadata;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.repository.item.ItemImageRepository;
import com.example.ourworldcup.repository.uuid.UuidCustomRepositoryImpl;
import com.example.ourworldcup.repository.uuid.UuidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ItemImageProcess extends FileProcessServiceImpl<ItemImagePackageMetadata> {
    private final ItemImageRepository itemImageRepository;
    @Autowired
    public ItemImageProcess(FileService amazonS3Service,
                            UuidCustomRepositoryImpl uuidCustomRepository,
                            UuidRepository uuidRepository,
                            ItemImageRepository itemImageRepository) {
        super(amazonS3Service, uuidCustomRepository, uuidRepository);
        this.itemImageRepository = itemImageRepository;
    }

    public Item uploadImageAndMapToItem(MultipartFile file, ItemImagePackageMetadata itemImagePackageMetadata, Item item) {
        Optional.ofNullable(item).orElseThrow(NullPointerException::new);
        String url = this.uploadImage(file, itemImagePackageMetadata);
        ItemImage itemImage = ItemImage.builder()
                .url(url)
                .uuid(itemImagePackageMetadata.getUuidEntity())
                .fileName(file.getOriginalFilename()).build();
        itemImageRepository.save(itemImage);
        item.setItemImage(itemImage);

        return item;
    }
}
