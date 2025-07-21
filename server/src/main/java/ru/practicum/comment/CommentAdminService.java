package ru.practicum.comment;

import java.util.List;

public interface CommentAdminService {

    void delete(Long comId);

    List<CommentDto> search(String text);

    List<CommentDto> findAllById(Long userId);
}