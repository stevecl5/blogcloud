package com.stevencl.css436.blogcloud;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.PartitionKey;
import com.azure.spring.data.cosmos.core.generator.QuerySpecGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseRepository {

    private final CosmosDatabase database;

    public DatabaseRepository(DatabaseConfig databaseConfig) throws Exception {
        CosmosClient client = databaseConfig.getCosmosClientBuilder().buildClient();
        this.database = client.getDatabase(databaseConfig.getDatabaseName());
    }

    public void listContainers() {
        var containers = database.readAllContainers();
        for (var container : containers) {
            System.out.println(container.getId());
        }
    }

}
