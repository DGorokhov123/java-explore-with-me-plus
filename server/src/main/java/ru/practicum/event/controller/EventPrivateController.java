package ru.practicum.event.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventPrivateService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    EventPrivateService eventPrivateService;

    @PostMapping
    EventFullDto addNewEventByUser(@PathVariable @Positive Long userId,
                                   @RequestBody NewEventDto newEventDto) {
        log.info("Calling to endpoint /users/{userId}/events PostMapping for userId: " + userId);
        return eventPrivateService.addEvent(userId, newEventDto);
    }

    @GetMapping
    List<EventShortDto> getAllEventsByUserId(@PathVariable @Positive Long userId,
                                             @RequestParam(defaultValue = "0") Long from,
                                             @RequestParam(defaultValue = "10") Long size) {
        log.info("Calling to endpoint /users/{userId}/events GetMapping for userId: " + userId);
        return eventPrivateService.getEventsByUserId(userId, from, size);
    }


    @GetMapping("/{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable @Positive Long userId,
                                            @PathVariable @Positive Long eventId) {
        log.info("Calling to endpoint /users/{userId}/events/{eventId} GetMapping for userId: "
                + userId + " and eventId: " + eventId);
        return eventPrivateService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    EventFullDto updateEventByUserIdAndEventId(@PathVariable @Positive Long userId,
                                               @PathVariable @Positive Long eventId,
                                               @RequestBody NewEventDto newEventDto) {
        log.info("Calling to endpoint /users/{userId}/events/{eventId} PatchMapping for userId: " + userId
                + " and eventId: " + eventId + "."
                + "Information by eventDto: " + newEventDto.toString());
        return eventPrivateService.updateEventByUserIdAndEventId(userId, eventId, newEventDto);
    }

    /*
        @GetMapping("/{eventId}/requests")
        List<ParticipationRequestDto> getInformationAboutAllRequestsByUserIdAndEventId(@PathVariable @Positive Long userId,
                                                                                       @PathVariable @Positive Long eventId) {
            log.info("Calling to endpoint /users/{userId}/{eventId}/requests GetMapping for userId: " + userId
                    + " and eventId: " + eventId);
            return eventPrivateService.getInformationAboutAllRequestsByUserIdAndEventId(userId, eventId);
        }


    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResult updateStatus(@PathVariable @Positive Long userId,
                                                @PathVariable @Positive Long eventId,
                                                @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Calling to endpoint /users/{userId}/{eventId}/requests PatchMapping for userId: " + userId
                + " and eventId: " + eventId + "."
                + " EventRequestStatusUpdateRequest: " + eventRequestStatusUpdateRequest.toString());
        return eventPrivateService.updateStates(userId, eventId, eventRequestStatusUpdateRequest);
    }
     */


}
