package ru.practicum.event.service;

import ru.practicum.event.dto.*;

import java.util.List;

public interface EventPrivateService {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    List<EventShortDto> getEventsByUserId(Long userId, Long from, Long size);


    EventFullDto updateEventByUserIdAndEventId(Long userId, Long eventId, NewEventDto newEventDto);

    //   List<ParticipationRequestDto> getInformationAboutAllRequestsByUserIdAndEventId(Long userId, Long eventId);

    // EventRequestStatusUpdateResult updateStates(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
