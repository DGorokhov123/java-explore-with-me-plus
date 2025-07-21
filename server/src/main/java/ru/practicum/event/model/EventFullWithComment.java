package ru.practicum.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.Category;
import ru.practicum.comment.Comment;
import ru.practicum.event.dto.Location;
import ru.practicum.event.dto.State;
import ru.practicum.user.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullWithComment {
    Long id;

    String annotation;

    Category category;

    String createdOn;

    String description;

    String eventDate;

    User initiator;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    State state;

    String title;

    Long views;

    Long confirmedRequests;

    String publishedOn;

    List<Comment> comments;
}