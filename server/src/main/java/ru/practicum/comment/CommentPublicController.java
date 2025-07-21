package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentPublicController {

    private final CommentPublicService service;

    @GetMapping("/comment/{comId}")
    public ResponseEntity<CommentDto> getById(@PathVariable Long comId) {
        log.info("Calling the GET request to /comment/{comId}endpoint");
        return ResponseEntity.ok(service.getComment(comId));
    }

    @GetMapping("/events/{eventId}/comment")
    public ResponseEntity<List<CommentShortDto>> getByEventId(@PathVariable Long eventId,
                                                              @RequestParam(defaultValue = "0") int from,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("Calling the GET request to /events/{eventId}/comment");
        return ResponseEntity.ok(service.getCommentsByEvent(eventId, from, size));
    }
}