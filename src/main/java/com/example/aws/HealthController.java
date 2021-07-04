package com.example.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<?> getHealth() throws UnknownHostException {
        HashMap<String, String> data = new HashMap<>();
        InetAddress localHost = InetAddress.getLocalHost();
        data.put("HostAddress: ", localHost.getHostAddress());
        data.put("HostName: ", localHost.getHostName());
        data.put("CanonicalHostName: ", localHost.getCanonicalHostName());
        return ResponseEntity.ok(data);
    }
}
