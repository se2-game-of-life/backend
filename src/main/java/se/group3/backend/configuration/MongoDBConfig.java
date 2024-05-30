package se.group3.backend.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "GameOfLife";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://ayasafan16:12345@cluster0.fqmjinm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
    }
}