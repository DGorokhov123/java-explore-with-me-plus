package ru.practicum.event.dto;

import ru.practicum.category.CategoryDto;
import ru.practicum.event.Location;
import ru.practicum.event.State;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;

public class EventFullDto {


    Long id;

    String annotation;

    CategoryDto category;

    Long confirmedRequester;

    LocalDateTime createdOn;

    String description;

    LocalDateTime eventDate;

    UserShortDto initiator;

    Location location;

    Boolean paid;

    Long participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    State state;

    String title;

    Long views;

}
