package com.example.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@RestController
public class HealthController {
    private AppConfig appConfig;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    public HealthController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealth() throws UnknownHostException {
        HashMap<String, String> data = new HashMap<>();
        InetAddress localHost = InetAddress.getLocalHost();
        data.put("HostAddress: ", localHost.getHostAddress());
        data.put("HostName: ", localHost.getHostName());
        data.put("CanonicalHostName: ", localHost.getCanonicalHostName());
        data.put("ENV", System.getProperty("ENV"));
        data.put("spring_profiles_active", activeProfile);
        data.put("aws.dynamoDB.accessKey", appConfig.getAwsConfig().getDynamoDB().getAccessKey());
        return ResponseEntity.ok(data);
    }
}
