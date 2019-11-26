package com.hgedu_server;

import com.hgedu_server.configs.StorageProperties;
import com.hgedu_server.repositories.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class HgeduServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HgeduServerApplication.class, args);
    }
    
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}
