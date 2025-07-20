package ru.practicum.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventAdminParams;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventAdminServiceImpl implements EventAdminService {

    EventRepository eventRepository;


    @Override
    public List<EventFullDto> getAllEventsByParams(EventAdminParams eventAdminParams) {

        return null;
    }
}
