package ru.practicum.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validation.CreateOrUpdateValidator;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDtoNew {

    private Set<Long> events = new HashSet<>();

    private Boolean pinned;

    @NotBlank(groups = {CreateOrUpdateValidator.Create.class})
    @Size(max = 50, groups = {CreateOrUpdateValidator.Create.class, CreateOrUpdateValidator.Update.class})
    private String title;
}