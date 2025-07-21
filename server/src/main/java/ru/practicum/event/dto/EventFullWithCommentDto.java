package ru.practicum.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.CategoryDto;
import ru.practicum.comment.CommentShortDto;
import ru.practicum.user.UserDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullWithCommentDto {
    Long id;

    String annotation;

    CategoryDto category;

    String createdOn;

    String description;

    String eventDate;

    UserDto initiator;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    State state;

    String title;

    Long views;

    Long confirmedRequests;

    String publishedOn;

    List<CommentShortDto> comments;
}