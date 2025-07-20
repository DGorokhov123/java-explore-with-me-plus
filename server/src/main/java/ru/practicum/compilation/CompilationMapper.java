package ru.practicum.compilation;

import lombok.experimental.UtilityClass;
import ru.practicum.category.CategoryMapper;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        List<EventShortDto> eventShortDtoList = compilation.getEvents().stream()
                .map(event ->
                        EventMapper.toEventShortDto(event, CategoryMapper.toCategoryDto(event.getCategory()),
                                UserMapper.toUserShortDto(event.getInitiator()))
                ).collect(Collectors.toList());
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventShortDtoList)
                .build();
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}