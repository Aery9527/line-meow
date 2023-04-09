package org.aery.line.meow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties
public class LineMeowApplication {

    public static void main(String[] args) {
//        Map<String, String> env = System.getenv();
//        env.forEach((key, val) -> System.out.println(key + ":" + val));
        SpringApplication.run(LineMeowApplication.class, args);
    }

}
