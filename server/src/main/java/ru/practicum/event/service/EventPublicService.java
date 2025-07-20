package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventParams;

import java.util.List;

public interface EventPublicService {

    List<EventFullDto> getAllEventsByParams(EventParams eventParams);

    EventFullDto getEventById(Long id);
}
