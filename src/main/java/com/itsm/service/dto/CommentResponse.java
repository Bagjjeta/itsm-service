package com.itsm.service.dto;

import com.itsm.service.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long ticketId,
        Long authorId,
        String authorFullName,
        String authorRole,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getTicket().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName(),
                comment.getAuthor().getRole().name(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
