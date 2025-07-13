package ru.practicum.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.validation.CreateOrUpdateValidator;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {

    Set<Long> events = new HashSet<>();

    Boolean pinned;

    @NotBlank(groups = {CreateOrUpdateValidator.Create.class})
    @Size(max = 50, groups = {CreateOrUpdateValidator.Create.class, CreateOrUpdateValidator.Update.class})
    String title;
}