package com.example.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
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
    @Profile("qa")
    public AmazonDynamoDB dynamoDBClient() {
        AwsConfig awsConfig = appConfig.getAwsConfig();
        AwsConfig.DynamoDB dynamoDBConfig = awsConfig.getDynamoDB();
        BasicAWSCredentials credentials = new BasicAWSCredentials(dynamoDBConfig.getAccessKey(), dynamoDBConfig.getSecretKey());
        final AmazonDynamoDB dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(awsConfig.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return dynamoDB;
    }

    @Bean
    @Profile("!qa")
    public AmazonDynamoDB dynamoDBClientNull() {
        return new NoOpAmazonDynamoDB();
    }
}
