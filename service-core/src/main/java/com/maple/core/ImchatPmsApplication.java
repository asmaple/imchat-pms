package com.maple.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.maple.common","com.maple.core"})
public class ImchatPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImchatPmsApplication.class, args);
    }

}
