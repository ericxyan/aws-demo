package com.example.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Getter
@Setter
@Configuration
public class AppConfig {
    private AwsConfig AwsConfig;
    private Environment env;

    @Autowired
    public AppConfig(com.example.aws.AwsConfig awsConfig, Environment env) {
        AwsConfig = awsConfig;
        this.env = env;
    }
}
