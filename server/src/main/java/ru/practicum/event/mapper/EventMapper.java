package ru.practicum.event.mapper;

import ru.practicum.category.Category;
import ru.practicum.category.CategoryDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.user.User;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;

public class EventMapper {


    public static Event toEvent(NewEventDto newEventDto, User owner, Category category) {
        return Event.builder()
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .createdOn(LocalDateTime.now())
                .category(category)
                .annotation(newEventDto.getAnnotation())
                .initiator(owner)
                .location(newEventDto.getLocation())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(State.PUBLISHED)
                .title(newEventDto.getTitle())
                .eventDate(newEventDto.getEventDate())
                .build();
    }


    public static EventFullDto toEventFullDto(Event saveEvent, CategoryDto categoryDto, UserShortDto userShortDto) {
        return new EventFullDto(saveEvent.getId(), saveEvent.getAnnotation(), categoryDto, saveEvent.getConfirmedRequester(),
                saveEvent.getCreatedOn(), saveEvent.getTitle(), saveEvent.getEventDate(), userShortDto, saveEvent.getLocation(), saveEvent.getPaid(),
                saveEvent.getParticipantLimit(), saveEvent.getPublishedOn(), saveEvent.getRequestModeration(), saveEvent.getState(), saveEvent.getTitle(), saveEvent.getViews());
    }

    public static EventShortDto toEventShortDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        return new EventShortDto(event.getId(), event.getAnnotation(), categoryDto, event.getConfirmedRequester(), event.getEventDate(),
                userShortDto, event.getPaid(), event.getTitle(), event.getViews());
    }



}
