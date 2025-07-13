package ru.practicum.compilation;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

    @Mapper(componentModel = "spring")
    public interface CompilationMapper {
        CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

        CompilationDto toCompilationDto(Compilation compilation);

        List<CompilationDto> toCompilationDtoList(List<Compilation> compilations);
    }