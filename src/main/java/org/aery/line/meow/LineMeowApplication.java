package org.aery.line.meow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LineMeowApplication {

    public static void main(String[] args) {
        SpringApplication.run(LineMeowApplication.class, args);
    }

}
