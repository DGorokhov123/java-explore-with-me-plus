package ru.practicum.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

import static ru.practicum.util.Util.createPageRequestAsc;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentPublicServiceImpl implements CommentPublicService {

    CommentRepository repository;
    EventRepository eventRepository;

    @Override
    public CommentDto getComment(Long comId) {
        log.info("getComment - invoked");
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> {
                    log.error("Comment with id = {} - not exist", comId);
                    return new NotFoundException("Comment not found");
                });
        log.info("Result: comment with id= {}", comId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentShortDto> getCommentsByEvent(Long eventId, int from, int size) {
        log.info("getCommentsByEvent - invoked");
        if (eventRepository.existsById(eventId)) {
            log.error("Event with id = {} - not exist", eventId);
            throw new NotFoundException("Event not found");
        }
        Pageable pageable = createPageRequestAsc("createTime", from, size);
        List<Comment> comments = repository.findAllByEventId(eventId, pageable);
        log.info("Result : list of comments size = {}", comments.size());
        return CommentMapper.toListCommentShortDto(comments);
    }

    @Override
    public CommentDto getCommentByEventAndCommentId(Long eventId, Long commentId) {
        log.info("getCommentByEventAndCommentId - invoked");
        Comment comment = repository.findByEventIdAndId(eventId, commentId)
                .orElseThrow(() -> {
                    log.error("Comment with eventId = {} and commentId = {} - not exist", eventId, commentId);
                    return new NotFoundException("Comment not found");
                });
        log.info("Result: comment with eventId= {} and commentId= {}", eventId, commentId);
        return CommentMapper.toCommentDto(comment);
    }
}