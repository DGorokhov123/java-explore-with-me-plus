package ru.practicum.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDtoFull {

    List<EventDtoFull> events;

    Long id;

    Boolean pinned;

    String title;
}