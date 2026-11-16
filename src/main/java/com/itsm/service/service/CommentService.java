package com.itsm.service.service;

import com.itsm.service.dto.CommentResponse;
import com.itsm.service.dto.CreateCommentRequest;
import com.itsm.service.exception.BadRequestException;
import com.itsm.service.exception.ResourceNotFoundException;
import com.itsm.service.model.Comment;
import com.itsm.service.model.Ticket;
import com.itsm.service.model.TicketStatus;
import com.itsm.service.model.User;
import com.itsm.service.repository.CommentRepository;
import com.itsm.service.repository.TicketRepository;
import com.itsm.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse addComment(Long ticketId, CreateCommentRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono zgłoszenia o ID: " + ticketId));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new BadRequestException("Nie można dodać komentarza do zamkniętego zgłoszenia (CLOSED).");
        }

        if (request.content() == null || request.content().isBlank()) {
            throw new BadRequestException("Treść komentarza nie może być pusta.");
        }

        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika (autora) o ID: " + request.authorId()));

        Comment comment = Comment.builder()
                .ticket(ticket)
                .author(author)
                .content(request.content())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.fromEntity(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByTicketId(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new ResourceNotFoundException("Nie znaleziono zgłoszenia o ID: " + ticketId);
        }

        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId).stream()
                .map(CommentResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Nie znaleziono komentarza o ID: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
