package com.itsm.service.dto;

public record CreateCommentRequest(
        Long authorId,
        String content
) {
}
