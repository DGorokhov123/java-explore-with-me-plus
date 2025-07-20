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
public class EventParams {

    String text;

    List<Long> categories;

    Boolean paid;

    LocalDateTime rangeStart;

    LocalDateTime rangeEnd;

    Boolean onlyAvailable;

    Sort sort;

    Long from;

    Long size;

}
