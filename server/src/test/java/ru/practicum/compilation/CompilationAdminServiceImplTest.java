package ru.practicum.compilation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;

class CompilationAdminServiceImplTest {

    @Mock
    private CompilationRepository compilationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private CompilationAdminServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompilation() {
        NewCompilationDto request = new NewCompilationDto();
        request.setPinned(true);
        request.setTitle("Test Compilation");
        request.setEvents(Set.of(1L, 2L));

        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(2L);
        when(eventRepository.findAllById(request.getEvents()))
                .thenReturn(new ArrayList<>(Set.of(event1, event2)));

        Compilation compilation = new Compilation();
        compilation.setId(1L);
        compilation.setPinned(true);
        compilation.setTitle("Test Compilation");
        compilation.setEvents(new HashSet<>(Set.of(event1, event2)));
        when(compilationRepository.save(any(Compilation.class))).thenReturn(compilation);

        CompilationDto result = service.createCompilation(request);
        assertNotNull(result);
        assertEquals(compilation.getId(), result.getId());
        assertEquals(compilation.getTitle(), result.getTitle());
        assertEquals(compilation.getPinned(), result.getPinned());
    }

    @Test
    void testTitleNotEmpty() {
        MockitoAnnotations.openMocks(this);
        Compilation compilation = new Compilation();
        compilation.setTitle("");

        when(validator.validate(compilation)).thenReturn(Validation.buildDefaultValidatorFactory().getValidator().validate(compilation));

        var violations = validator.validate(compilation);
        assertTrue(violations.iterator().hasNext(), "Title should not be empty");
    }

    @Test
    void testTitleMaxLength() {
        MockitoAnnotations.openMocks(this);
        Compilation compilation = new Compilation();
        compilation.setTitle("This title is too long and exceeds the maximum allowed length of 50 characters");

        when(validator.validate(compilation)).thenReturn(Validation.buildDefaultValidatorFactory().getValidator().validate(compilation));

        var violations = validator.validate(compilation);
        assertTrue(violations.iterator().hasNext(), "Title should not exceed 50 characters");
    }

    @Test
    void testDeleteCompilation() {
        Long compId = 1L;
        when(compilationRepository.existsById(compId)).thenReturn(true);
        service.deleteCompilation(compId);
        verify(compilationRepository, times(1)).deleteById(compId);
    }

    @Test
    void testUpdateCompilation() {
        Long compId = 1L;
        NewCompilationDto newCompilationDto = new NewCompilationDto();
        newCompilationDto.setTitle("Updated Compilation");

        Compilation existingCompilation = new Compilation();
        existingCompilation.setId(compId);
        existingCompilation.setTitle("Old Compilation");
        when(compilationRepository.findById(compId))
                .thenReturn(Optional.of(existingCompilation));

        Compilation updatedCompilation = new Compilation();
        updatedCompilation.setId(compId);
        updatedCompilation.setTitle("Updated Compilation");
        when(compilationRepository.save(any(Compilation.class)))
                .thenReturn(updatedCompilation);

        CompilationDto result = service.updateCompilation(compId, newCompilationDto);
        assertNotNull(result);
        assertEquals(updatedCompilation.getId(), result.getId());
        assertEquals(updatedCompilation.getTitle(), result.getTitle());
    }
}