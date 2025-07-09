package ru.practicum;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.ewm.client.StatClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@ComponentScan({"ru.practicum.ewm.client"})
public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        EventHitDto hit =   EventHitDto.builder()
                .app("app1")
                .uri("/events/2")
                .ip("192.168.1.2")
                .timestamp(now.minusHours(2))
                .build();
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        ApplicationContext context = SpringApplication.run(Main.class, args);
        StatClient statClient = context.getBean(StatClient.class);
        statClient.hit(hit);
        Collection<EventStatsResponseDto> eventStatsResponseDtoCollection = statClient.stats(start, end, null, true);
        System.out.println(eventStatsResponseDtoCollection.size());
    }
}
