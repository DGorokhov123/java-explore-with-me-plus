package ru.practicum.event.service;

import jakarta.validation.Valid;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;

import java.util.List;

public interface EventPrivateService {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    List<EventShortDto> getEventsByUserId(Long userId, Long from, Long size);


    EventFullDto updateEventByUserIdAndEventId(Long userId, Long eventId, @Valid UpdateEventUserRequest newEventDto);

}
