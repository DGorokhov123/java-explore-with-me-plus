package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    @NotBlank
    String annotation;

    @NotBlank
    Long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    String description;

    @Future(message = "Дата события должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime eventDate;

    @NotNull
    Location location;

    @NotNull
    Boolean paid;

    @JsonProperty(defaultValue = "0")
    Long participantLimit;

    @NotNull
    Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)
    String title;

}
