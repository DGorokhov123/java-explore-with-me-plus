package ru.practicum.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.dto.EventCommentDto;
import ru.practicum.serialize.LocalDateTimeSerializer;
import ru.practicum.user.UserDto;

import java.time.LocalDateTime;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    Boolean approved;
}