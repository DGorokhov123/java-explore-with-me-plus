package ru.practicum.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.category.Category;
import ru.practicum.category.CategoryDto;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.CategoryRepository;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.ParticipationRequestStatus;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;
import ru.practicum.user.UserRepository;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {


    UserRepository userRepository;
    CategoryRepository categoryRepository;
    EventRepository eventRepository;


    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User owner = userRepository.getById(userId);
        Category categoryForSaveEvent = categoryRepository.getReferenceById(newEventDto.getCategory());
        Event saveEvent = EventMapper.toEvent(newEventDto, owner, categoryForSaveEvent);
        eventRepository.save(saveEvent);
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(categoryForSaveEvent);
        UserShortDto userShortDto = UserMapper.toUserShortDto(owner);
        Long requesterCount = eventRepository.countByEventIdAndStatus(saveEvent.getId(), ParticipationRequestStatus.CONFIRMED);
        return EventMapper.toEventFullDto(saveEvent, categoryDto, userShortDto, requesterCount);
    }

    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        Event eventByUserIdAndEventId = eventRepository.findByIdAndInitiator(eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with id=%d and initiator id=%d not found", eventId, userId)));
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(eventByUserIdAndEventId.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(eventByUserIdAndEventId.getUser());
        Long requesterCount = eventRepository.countByEventIdAndStatus(eventByUserIdAndEventId.getId(), ParticipationRequestStatus.CONFIRMED);
        return EventMapper.toEventFullDto(eventByUserIdAndEventId, categoryDto, userShortDto, requesterCount);
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Long from, Long size) {
        existsUser(userId);

        Pageable pageable = PageRequest.of(
                from.intValue() / size.intValue(),
                size.intValue(),
                Sort.by("eventDate").descending());

        List<Event> events = eventRepository.findByInitiator(userId, pageable);

        return events.stream()
                .map(event -> {
                    CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
                    UserShortDto userShortDto = UserMapper.toUserShortDto(event.getUser());
                    Long requesterCount = eventRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED);
                    return EventMapper.toEventShortDto(event, categoryDto, userShortDto, requesterCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByUserIdAndEventId(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        existsUser(userId);
        Event event = eventRepository.findByIdAndInitiator(eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with id=%d and initiator id=%d not found", eventId, userId)));
        if (event.getState() != State.PENDING && event.getState() != State.CANCELED) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }
        if (updateEventUserRequest.getEventDate() != null &&
                updateEventUserRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Event date must be at least 2 hours from now");
        }
        updateFieldsByEvent(updateEventUserRequest, event);
        event.setState(State.PENDING);
        Event updatedEvent = eventRepository.save(event);
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(updatedEvent.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(updatedEvent.getUser());
        Long requesterCount = eventRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED);
        return EventMapper.toEventFullDto(updatedEvent, categoryDto, userShortDto, requesterCount);
    }


    private void existsUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
    }

    private void updateFieldsByEvent(UpdateEventUserRequest newEventDto, Event event) {
        if (newEventDto.getAnnotation() != null) {
            event.setAnnotation(newEventDto.getAnnotation());
        }

        if (newEventDto.getCategory() != null) {
            Category category = categoryRepository.findById(newEventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException(
                            "Category with id=" + newEventDto.getCategory() + " not found"));
            event.setCategory(category);
        }

        if (newEventDto.getDescription() != null) {
            event.setAnnotation(newEventDto.getDescription());
        }

        if (newEventDto.getEventDate() != null) {
            event.setEventDate(newEventDto.getEventDate());
        }

        if (newEventDto.getPaid() != null) {
            event.setPaid(newEventDto.getPaid());
        }

        if (newEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }

        if (newEventDto.getTitle() != null) {
            event.setTitle(newEventDto.getTitle());
        }
    }

}
