package ru.practicum.event.mapper;

import ru.practicum.category.Category;
import ru.practicum.category.CategoryMapper;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;

import java.time.LocalDateTime;

public class EventMapper {


    public static Event toEvent(
            NewEventDto newEventDto,
            User initiator,
            Category category
    ) {
        return Event.builder()
                .initiator(initiator)
                .category(category)
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .state(State.PENDING)
                .location(newEventDto.getLocation())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .createdOn(LocalDateTime.now())
                .build();
    }


    public static EventFullDto toEventFullDto(
            Event event,
            Long confirmedRequests,
            Long views
    ) {
        if (confirmedRequests == null) confirmedRequests = 0L;
        return EventFullDto.builder()
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .state(event.getState())
                .location(event.getLocation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .createdOn(event.getCreatedOn())
                .confirmedRequests(confirmedRequests)
                .views(views)
                .build();
    }

    public static EventShortDto toEventShortDto(
            Event event,
            Long confirmedRequests,
            Long views
    ) {
        if (confirmedRequests == null) confirmedRequests = 0L;
        return EventShortDto.builder()
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .confirmedRequests(confirmedRequests)
                .views(views)
                .build();
    }

}
