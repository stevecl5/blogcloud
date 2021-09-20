package com.stevencl.blogcloud.config;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * https://docs.microsoft.com/en-us/java/api/overview/azure/spring-data-cosmos-readme?view=azure-java-stable
 */
@Configuration
@EnableCosmosRepositories
public class DatabaseConfig extends AbstractCosmosConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${azure.cosmos.uri}")
    private String uri;
    @Value("${azure.cosmos.key}")
    private String key;
    @Value("${azure.cosmos.secondary-key}")
    private String secondaryKey;
    @Value("${azure.cosmos.database}")
    private String dbName;
    @Value("${azure.cosmos.populate-query-metrics}")
    private boolean populateQueryMetrics;

    private AzureKeyCredential azureKeyCredential;

    public CosmosClientBuilder getCosmosClientBuilder() {
        this.azureKeyCredential = new AzureKeyCredential(key);
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        return new CosmosClientBuilder()
                .endpoint(uri)
                .credential(azureKeyCredential)
                .directMode(directConnectionConfig, gatewayConnectionConfig);
    }

    @Override
    public CosmosConfig cosmosConfig() {
        return CosmosConfig.builder()
                .enableQueryMetrics(populateQueryMetrics)
                .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
                .build();
    }

    public void switchToSecondaryKey() {
        this.azureKeyCredential.update(secondaryKey);
    }

    public void switchToPrimaryKey() {
        this.azureKeyCredential.update(key);
    }

    public void switchKey(String key) {
        this.azureKeyCredential.update(key);
    }

    @Override
    protected String getDatabaseName() {
        return this.dbName;
    }

    private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

        @Override
        public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
            LOGGER.info("Response Diagnostics {}", responseDiagnostics);
        }
    }

}
