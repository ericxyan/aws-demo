package com.example.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class AppConfig {
    private AwsConfig AwsConfig;

    @Autowired
    public AppConfig(com.example.aws.AwsConfig awsConfig) {
        AwsConfig = awsConfig;
    }
}
