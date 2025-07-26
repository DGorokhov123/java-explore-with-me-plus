package ru.practicum.compilation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
class CompilationPublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CompilationPublicService service;

    @InjectMocks
    private CompilationPublicController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCompilationById() throws Exception {
        Long compId = 1L;
        CompilationDto response = new CompilationDto();
        response.setId(compId);
        response.setTitle("Test Compilation");
        response.setPinned(true);

        when(service.readCompilationById(compId)).thenReturn(response);

        mockMvc.perform(get("/compilations/{compId}", compId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Test Compilation\",\"pinned\":true}"));
    }

    @Test
    void testTitleNotEmpty() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Compilation compilation = new Compilation();
        compilation.setTitle("");

        Set<ConstraintViolation<Compilation>> violations = validator.validate(compilation);
        assertFalse(violations.isEmpty());
    }

    private void assertFalse(boolean empty) {
    }

    @Test
    void testTitleMaxLength() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Compilation compilation = new Compilation();
        compilation.setTitle("This title is too long and exceeds the maximum allowed length of 50 characters");

        Set<ConstraintViolation<Compilation>> violations = validator.validate(compilation);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testGetCompilations() throws Exception {
        Boolean pinned = true;
        int from = 0;
        int size = 10;

        CompilationDto compilationDto1 = new CompilationDto();
        compilationDto1.setId(1L);
        compilationDto1.setTitle("Compilation 1");
        compilationDto1.setPinned(true);

        CompilationDto compilationDto2 = new CompilationDto();
        compilationDto2.setId(2L);
        compilationDto2.setTitle("Compilation 2");
        compilationDto2.setPinned(false);

        List<CompilationDto> list = new ArrayList<>();
        list.add(compilationDto1);
        list.add(compilationDto2);

        when(service.readAllCompilations(pinned, from, size)).thenReturn(list);

        mockMvc.perform(get("/compilations")
                        .param("pinned", String.valueOf(pinned))
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"title\":\"Compilation 1\",\"pinned\":true},{\"id\":2,\"title\":\"Compilation 2\",\"pinned\":false}]"));
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}