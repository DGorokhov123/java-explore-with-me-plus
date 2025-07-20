package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventParams;

import java.util.List;

public class EventPublicServiceImpl implements EventPublicService {
    @Override
    public List<EventFullDto> getAllEventsByParams(EventParams eventParams) {
        return List.of();
    }

    @Override
    public EventFullDto getEventById(Long id) {
        return null;
    }
}
