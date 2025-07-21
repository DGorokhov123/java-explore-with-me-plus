package ru.practicum.comment;

import ru.practicum.event.model.Event;

import java.util.Collection;
import java.util.Map;

public interface CommentPrivateService {

    CommentDto createComment(Long userId, Long eventId, CommentCreateDto commentDto);

    void deleteComment(Long userId, Long comId);

    CommentDto patchComment(Long userId, Long comId, CommentCreateDto commentCreateDto);

    Map<Long, Long> getCommentCount(Collection<Event> list);

    Map<Long, Long> getCommentCount(Collection<Event> list);
}