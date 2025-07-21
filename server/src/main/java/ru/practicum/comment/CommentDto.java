package ru.practicum.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.dto.EventCommentDto;
import ru.practicum.user.UserDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    long id;

    String text;

    UserDto author;

    EventCommentDto event;

    String createTime;
}