package com.example.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {
    private String region;
    private DynamoDBCredential dynamoDB;

    @Getter
    @Setter
    public static class DynamoDBCredential {
        private String accessKey;
        private String secretKey;
    }
}
