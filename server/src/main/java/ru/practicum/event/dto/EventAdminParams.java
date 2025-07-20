package ru.practicum.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EventAdminParams {

    List<Long> users;

    List<State> states;

    List<Long> categories;

    LocalDateTime rangeStart;

    LocalDateTime rangeEnd;

    Long from;

    Long size;

}
