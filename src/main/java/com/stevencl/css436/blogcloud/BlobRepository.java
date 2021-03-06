package com.stevencl.css436.blogcloud;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class BlobRepository {

    @Value("${azure.storage.container-url}")
    private String containerUrl;

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    private BlobContainerClient container;

    public BlobRepository() {
        //
    }

    @PostConstruct
    private void setContainerClient() {
        var accountCredential = new StorageSharedKeyCredential(accountName, accountKey);
        this.container = new BlobContainerClientBuilder()
                .endpoint(containerUrl)
                .credential(accountCredential)
                .buildClient();
    }

    public void getContainerName() {
        System.out.println(container.getBlobContainerName());
    }

}
