package com.itsm.service.controller;

import com.itsm.service.dto.CommentResponse;
import com.itsm.service.dto.CreateCommentRequest;
import com.itsm.service.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Komunikacja i komentarze wewnątrz zgłoszeń ITSM")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("tickets/{ticketId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Dodaj komentarz do zgłoszenia")
    public CommentResponse addComment(
            @PathVariable Long ticketId,
            @RequestBody CreateCommentRequest request
    ) {
        return commentService.addComment(ticketId, request);
    }

    @GetMapping("/tickets/{ticketId}/comments")
    @Operation(summary = "Pobierz listę komentarzy dla zgłoszenia (chronologicznie)")
    public List<CommentResponse> getCommentsByTicketId(@PathVariable Long ticketId) {
        return commentService.getCommentsByTicketId(ticketId);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń komentarz po jego ID")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
