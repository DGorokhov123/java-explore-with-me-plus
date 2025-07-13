package ru.practicum.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.EventShortDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDtoShort {

    List<EventShortDto> events;

    Long id;

    Boolean pinned;

    String title;

}