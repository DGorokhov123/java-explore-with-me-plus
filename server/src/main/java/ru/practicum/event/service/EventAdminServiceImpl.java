package ru.practicum.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.CategoryRepository;
import ru.practicum.event.dto.EventAdminParams;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.State;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.ParticipationRequestStatus;
import ru.practicum.user.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventAdminServiceImpl implements EventAdminService {

    EventRepository eventRepository;
    CategoryRepository categoryRepository;

    @Override
    public List<EventFullDto> getAllEventsByParams(EventAdminParams eventAdminParams) {
        Pageable pageable = PageRequest.of(
                eventAdminParams.getFrom().intValue() / eventAdminParams.getSize().intValue(),
                eventAdminParams.getSize().intValue());

        return eventRepository.findEventsByAdminParams(
                        eventAdminParams.getUsers(),
                        eventAdminParams.getStates(),
                        eventAdminParams.getCategories(),
                        eventAdminParams.getRangeStart(),
                        eventAdminParams.getRangeEnd(),
                        pageable)
                .stream()
                .map(event -> {
                    return EventMapper.toEventFullDto(event, CategoryMapper.toCategoryDto(event.getCategory()),
                            UserMapper.toUserShortDto(event.getUser()),
                            eventRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED));
                })
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));


        if (updateEventUserRequest.getEventDate() != null) {
            LocalDateTime newEventDate = updateEventUserRequest.getEventDate();
            if (newEventDate.isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ConflictException("Event date must be at least 1 hour from now");
            }
            event.setEventDate(newEventDate);
        }


        if (updateEventUserRequest.getStateAction() != null) {
            switch (updateEventUserRequest.getStateAction()) {
                case SEND_TO_REVIEW:
                    if (event.getState() != State.PENDING) {
                        throw new ConflictException("Cannot publish event that is not in PENDING state");
                    }
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case CANCEL_REVIEW:
                    if (event.getState() == State.PUBLISHED) {
                        throw new ConflictException("Cannot reject already published event");
                    }
                    event.setState(State.CANCELED);
                    break;
            }
        }


        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventUserRequest.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found")));
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }

        Event updatedEvent = eventRepository.save(event);
        Long confirmedRequests = eventRepository.countConfirmedRequests(eventId);

        return EventMapper.toEventFullDto(
                updatedEvent,
                CategoryMapper.toCategoryDto(updatedEvent.getCategory()),
                UserMapper.toUserShortDto(updatedEvent.getUser()),
                confirmedRequests
        );
    }
}
