package ru.practicum.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDtoShort {

    List<EventDtoShort> events;

    Long id;

    Boolean pinned;

    String title;

}