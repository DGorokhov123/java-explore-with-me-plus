package ru.practicum.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.dto.EventFullDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDtoFull {

    List<EventFullDto> events;

    Long id;

    Boolean pinned;

    String title;
}