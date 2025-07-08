package ru.practicum.model.mapper;

import ru.practicum.EventHitDto;
import ru.practicum.model.Stat;

public class StatMapper {

    private StatMapper() {
    }

    public static Stat toStat(EventHitDto statDto) {

        return Stat.builder()
                .app(statDto.getApp())
                .uri(statDto.getUri())
                .ip(statDto.getIp())
                .timestamp(statDto.getTimestamp())
                .build();
    }

    public static EventHitDto toEventHitDto(Stat stat) {
        return EventHitDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .ip(stat.getIp())
                .timestamp(stat.getTimestamp())
                .build();
    }

}