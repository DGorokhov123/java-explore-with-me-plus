package ru.practicum.comment;

import lombok.Data;

@Data
public class CommentCountDto {

    private Long eventId;
    private Long commentCount;

    public CommentCountDto(Long eventId, Long commentCount) {
        this.eventId = eventId;
        this.commentCount = commentCount;
    }
}