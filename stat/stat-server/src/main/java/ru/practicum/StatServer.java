package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Import(WebConfig.class)
public class StatServer {

    public static void main(String[] args) {
        SpringApplication.run(StatServer.class, args);
    }

}
