package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CompilationPublicServiceImplTest {
    @Mock
    private CompilationRepository compilationRepository;

    @InjectMocks
    private CompilationPublicServiceImpl compilationPublicServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadCompilationById_CompilationFound() {
        Compilation compilation = new Compilation();
        compilation.setId(1L);
        compilation.setTitle("Test Compilation");

        when(compilationRepository.findById(1L)).thenReturn(Optional.of(compilation));

        CompilationDto compilationDto = compilationPublicServiceImpl.readCompilationById(1L);
        assertEquals(compilation.getId(), compilationDto.getId());
        assertEquals(compilation.getTitle(), compilationDto.getTitle());
    }

    @Test
    void testReadCompilationById_CompilationNotFound() {
        when(compilationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> compilationPublicServiceImpl.readCompilationById(1L));
    }

    @Test
    void testReadAllCompilations_PinnedNull() {
        Compilation compilation1 = new Compilation();
        compilation1.setId(1L);
        compilation1.setTitle("Test Compilation 1");

        Compilation compilation2 = new Compilation();
        compilation2.setId(2L);
        compilation2.setTitle("Test Compilation 2");

        List<Compilation> compilations = new ArrayList<>();
        compilations.add(compilation1);
        compilations.add(compilation2);

        when(compilationRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(compilations));

        List<CompilationDto> compilationDtos = compilationPublicServiceImpl.readAllCompilations(null, 0, 10);
        assertEquals(2, compilationDtos.size());
        assertEquals(compilation1.getId(), compilationDtos.get(0).getId());
        assertEquals(compilation1.getTitle(), compilationDtos.get(0).getTitle());
        assertEquals(compilation2.getId(), compilationDtos.get(1).getId());
        assertEquals(compilation2.getTitle(), compilationDtos.get(1).getTitle());
    }

    @Test
    void testReadAllCompilations_PinnedTrue() {
        Compilation compilation1 = new Compilation();
        compilation1.setId(1L);
        compilation1.setTitle("Test Compilation 1");
        compilation1.setPinned(true);

        Compilation compilation2 = new Compilation();
        compilation2.setId(2L);
        compilation2.setTitle("Test Compilation 2");
        compilation2.setPinned(false);

        List<Compilation> compilations = new ArrayList<>();
        compilations.add(compilation1);

        when(compilationRepository.findAllByPinned(true, any(Pageable.class))).thenReturn((List<Compilation>) new PageImpl<>(compilations));

        List<CompilationDto> compilationDtos = compilationPublicServiceImpl.readAllCompilations(true, 0, 10);
        assertEquals(1, compilationDtos.size());
        assertEquals(compilation1.getId(), compilationDtos.get(0).getId());
        assertEquals(compilation1.getTitle(), compilationDtos.get(0).getTitle());
    }
}