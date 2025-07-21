package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.EventHitDto;
import ru.practicum.category.CategoryMapper;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventParams;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.State;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.ParticipationRequestStatus;
import ru.practicum.user.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventPublicServiceImpl implements EventPublicService {

    EventRepository eventRepository;
    StatClient statClient;

    @Override
    public List<EventShortDto> getAllEventsByParams(EventParams eventParams, HttpServletRequest request) {
        statClient.hit(EventHitDto.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app("ewm-main-service")
                .timestamp(LocalDateTime.now())
                .build());

        LocalDateTime rangeStart = eventParams.getRangeStart() != null ?
                eventParams.getRangeStart() : LocalDateTime.now();
        LocalDateTime rangeEnd = eventParams.getRangeEnd();


        PageRequest pageRequest = PageRequest.of(
                eventParams.getFrom().intValue() / eventParams.getSize().intValue(),
                eventParams.getSize().intValue(),
                getSort(eventParams.getSort())
        );

        Page<Event> eventPage = eventRepository.findPublicEvents(
                eventParams.getText(),
                eventParams.getCategories(),
                eventParams.getPaid(),
                rangeStart,
                rangeEnd,
                eventParams.getOnlyAvailable(),
                pageRequest
        );


        return eventPage.getContent().stream()
                .map(event -> EventMapper.toEventShortDto(
                        event,
                        CategoryMapper.toCategoryDto(event.getCategory()),
                        UserMapper.toUserShortDto(event.getUser()),
                        eventRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED)))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        event.setViews(event.getViews() + 1);
        eventRepository.save(event);

        statClient.hit(EventHitDto.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app("ewm-main-service")
                .timestamp(LocalDateTime.now())
                .build());

        return EventMapper.toEventFullDto(
                event,
                CategoryMapper.toCategoryDto(event.getCategory()),
                UserMapper.toUserShortDto(event.getUser()),
                eventRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED));
    }

    private org.springframework.data.domain.Sort getSort(ru.practicum.event.dto.Sort sort) {
        if (sort == null) {
            return org.springframework.data.domain.Sort.by(Sort.Direction.ASC, "eventDate");
        }

        switch (sort) {
            case VIEWS:
                return org.springframework.data.domain.Sort.by(Sort.Direction.DESC, "views");
            case EVENT_DATE:
            default:
                return org.springframework.data.domain.Sort.by(Sort.Direction.ASC, "eventDate");
        }
    }
}