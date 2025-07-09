package ru.practicum;


import ewm.client.StatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@ComponentScan(value = {"ewm/client"})
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        StatClient statClient = context.getBean(StatClient.class);
        statClient.hit(new EventHitDto("a", "b", "1", LocalDateTime.now()));
        List<EventStatsResponseDto> eventStatsResponseDtoCollection = (List<EventStatsResponseDto>) statClient.stats(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), List.of(), false);
        System.out.println(eventStatsResponseDtoCollection.getFirst());
    }
}
