package ru.practicum.event.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.dto.EventAdminParams;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.State;
import ru.practicum.event.service.EventAdminService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {

    EventAdminService eventAdminService;

    @GetMapping
    List<EventFullDto> getAllEventsByParams(@RequestParam List<Long> users,
                                            @RequestParam List<State> states,
                                            @RequestParam List<Long> categories,
                                            @RequestParam LocalDateTime rangeStart,
                                            @RequestParam LocalDateTime rangeEnd,
                                            @RequestParam Long from,
                                            @RequestParam Long size) {
        log.info("Called ");
        EventAdminParams eventAdminParams = new EventAdminParams(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventAdminService.getAllEventsByParams(eventAdminParams);
    }


}
