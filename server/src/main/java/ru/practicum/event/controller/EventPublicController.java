package ru.practicum.event.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventParams;
import ru.practicum.event.dto.Sort;
import ru.practicum.event.service.EventPublicService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EventPublicController {

    EventPublicService eventPublicService;

    @GetMapping
    List<EventFullDto> getAllEventsByParams(@RequestParam String text,
                                            @RequestParam List<Long> categories,
                                            @RequestParam Boolean paid,
                                            @RequestParam LocalDateTime rangeStart,
                                            @RequestParam LocalDateTime rangeEnd,
                                            @RequestParam Boolean onlyAvailable,
                                            @RequestParam Sort sort,
                                            @RequestParam Long from,
                                            @RequestParam Long size,
                                            HttpServletRequest request) {
        EventParams eventParams = new EventParams(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Calling to endpoint /events GetMapping for params: " + eventParams.toString());
        return eventPublicService.getAllEventsByParams(eventParams, request);
    }


    @GetMapping("/{id}")
    EventFullDto getInformationAboutEventByEventId(@PathVariable @Positive Long id,
                                                   HttpServletRequest request) {
        log.info("Calling to endpoint /events/{id} GetMapping for eventId: " + id);
        return eventPublicService.getEventById(id, request);
    }

}
