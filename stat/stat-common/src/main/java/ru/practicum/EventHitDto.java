package ru.practicum;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.serialize.LocalDateTimeDeserializer;
import ru.practicum.serialize.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventHitDto {

    @NotNull(message = "Field 'app' should not be null")
    private String app;

    @NotNull(message = "Field 'uri' should not be null")
    private String uri;

    @NotNull(message = "Field 'ip' should not be null")
    private String ip;

    @NotNull(message = "Field 'timestamp' should not be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

}
