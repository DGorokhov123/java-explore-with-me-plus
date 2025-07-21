package ru.practicum.event.mapper;

import ru.practicum.category.Category;
import ru.practicum.category.CategoryDto;
import ru.practicum.event.dto.*;
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
                .user(owner)
                .location(newEventDto.getLocation())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(State.PUBLISHED)
                .title(newEventDto.getTitle())
                .eventDate(newEventDto.getEventDate())
                .build();
    }


    public static EventFullDto toEventFullDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto, Long confirmedRequester) {
        return EventFullDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .state(event.getState())
                .views(event.getViews())
                .participantLimit(event.getParticipantLimit())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequester(confirmedRequester)
                .requestModeration(event.getRequestModeration())
                .publishedOn(event.getPublishedOn())
                .createdOn(event.getCreatedOn())
                .location(event.getLocation())
                .title(event.getTitle())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto, Long confirmedRequester) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .categoryDto(categoryDto)
                .confirmedRequests(confirmedRequester)
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventCommentDto toEventComment(Event event) {
        return EventCommentDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .build();
    }
}
