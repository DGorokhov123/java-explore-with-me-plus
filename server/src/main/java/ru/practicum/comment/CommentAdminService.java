package ru.practicum.comment;

import java.util.List;

public interface CommentAdminService {

    void delete(Long comId);

    List<CommentDto> search(String text, int from, int size);

    List<CommentDto> findAllByUserId(Long userId, int from, int size);

    CommentDto approveComment(Long comId);

    CommentDto rejectComment(Long comId);
}