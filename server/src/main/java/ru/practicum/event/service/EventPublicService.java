package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventParams;

import java.util.List;

public interface EventPublicService {

    List<EventFullDto> getAllEventsByParams(EventParams eventParams, HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request);
}
