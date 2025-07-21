package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.CategoryDto;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

    Long id;

    @NotBlank
    String annotation;

    @NotBlank
    String description;

    @NotNull
    CategoryDto categoryDto;

    @NotNull
    Long confirmedRequests;

    @NotNull
    @Future(message = "Дата события должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime eventDate;

    @NotNull
    UserShortDto initiator;

    @NotNull
    Boolean paid;

    @NotBlank
    String title;

    @Size(min = 1, max = 999)
    Long views;

    public EventShortDto() {
    }
}
