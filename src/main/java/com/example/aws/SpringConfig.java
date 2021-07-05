package com.example.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SpringConfig {
    private AppConfig appConfig;

    @Autowired
    public SpringConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    @Profile("dev")
    public AmazonDynamoDB dynamoDBClient() throws JsonProcessingException {
        AWSSecretsManagerClient client = new AWSSecretsManagerClient();
        String secret = client.getSecret();
        ObjectMapper mapper = new ObjectMapper();
        AwsConfig.DynamoDBCredential dynamoDBConfig = mapper.readValue(secret, AwsConfig.DynamoDBCredential.class);
        AwsConfig awsConfig = appConfig.getAwsConfig();
        BasicAWSCredentials credentials = new BasicAWSCredentials(dynamoDBConfig.getAccessKey(), dynamoDBConfig.getSecretKey());
        final AmazonDynamoDB dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(awsConfig.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return dynamoDB;
    }

    @Bean
    @Profile("!dev")
    public AmazonDynamoDB dynamoDBClientNull() {
        return new NoOpAmazonDynamoDB();
    }
}
