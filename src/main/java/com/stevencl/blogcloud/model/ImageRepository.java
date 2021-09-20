package com.stevencl.blogcloud.model;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ImageRepository {

    @Value("${azure.storage.container-url}")
    private String containerUrl;
    @Value("${azure.storage.account-name}")
    private String accountName;
    @Value("${azure.storage.account-key}")
    private String accountKey;

    private BlobContainerClient images;

    public ImageRepository() {
        //
    }

    @PostConstruct
    private void setContainerClient() {
        var accountCredential = new StorageSharedKeyCredential(accountName, accountKey);
        this.images = new BlobContainerClientBuilder()
                .endpoint(containerUrl)
                .credential(accountCredential)
                .buildClient();
    }

    public void getContainerName() {
        System.out.println(images.getBlobContainerName());
    }

}
