package ru.practicum.compilation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.event.EventShortDto;

import java.util.List;
import java.util.Set;
import jakarta.validation.Validator;

@SpringBootTest
@AutoConfigureMockMvc
class CompilationAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Validator validator;

    @Mock
    private CompilationAdminService service;

    @InjectMocks
    private CompilationAdminController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostCompilations() throws Exception {
        NewCompilationDto newCompilationDto = new NewCompilationDto();
        newCompilationDto.setEvents(Set.of(1L, 2L, 3L));
        newCompilationDto.setPinned(true);
        newCompilationDto.setTitle("Test Compilation");

        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setEvents(List.of(new EventShortDto(), new EventShortDto()));
        compilationDto.setId(100L);
        compilationDto.setPinned(true);
        compilationDto.setTitle("Created Compilation");
        when(service.createCompilation(newCompilationDto)).thenReturn(compilationDto);

        mockMvc.perform(post("/admin/compilations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCompilationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(compilationDto)));
    }

    @Test
    void testTitleMinLength() {
        Compilation compilation = new Compilation();
        compilation.setTitle("");
        Set<ConstraintViolation<Compilation>> violations = validator.validate(compilation);
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().contains("size must be between 1 and 50")));
    }

    @Test
    void testTitleMaxLength() {
        Compilation compilation = new Compilation();
        compilation.setTitle("a".repeat(51));
        Set<ConstraintViolation<Compilation>> violations = validator.validate(compilation);
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().contains("size must be between 1 and 50")));
    }

    @Test
    void testTitleNotEmpty() {
        Compilation compilation = new Compilation();
        compilation.setTitle(null);
        Set<ConstraintViolation<Compilation>> violations = validator.validate(compilation);
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().contains("must not be empty")));
    }

    @Test
    void testDeleteCompilation() throws Exception {
        Long compId = 1L;
        doNothing().when(service).deleteCompilation(compId);
        mockMvc.perform(delete("/admin/compilations/{compId}", compId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchCompilation() throws Exception {
        Long compId = 1L;
        NewCompilationDto newCompilationDto = new NewCompilationDto();
        newCompilationDto.setEvents(Set.of(1L, 2L, 3L));
        newCompilationDto.setPinned(true);
        newCompilationDto.setTitle("Updated Compilation");

        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setEvents(List.of(new EventShortDto(), new EventShortDto()));
        compilationDto.setId(compId);
        compilationDto.setPinned(true);
        compilationDto.setTitle("Updated Compilation");
        when(service.updateCompilation(compId, newCompilationDto)).thenReturn(compilationDto);

        mockMvc.perform(patch("/admin/compilations/{compId}", compId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCompilationDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(compilationDto)));
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
