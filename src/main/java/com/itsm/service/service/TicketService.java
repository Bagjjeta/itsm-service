package com.itsm.service.service;

import com.itsm.service.dto.CreateTicketRequest;
import com.itsm.service.dto.TicketResponse;
import com.itsm.service.dto.UpdateTicketRequest;
import com.itsm.service.model.*;
import com.itsm.service.repository.TicketRepository;
import com.itsm.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketResponse createTicket(CreateTicketRequest request) {
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nie znaleziono użytkownika zgłaszającego o ID: " + request.getReporterId()
                ));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());
        ticket.setReporter(reporter);
        ticket.setStatus(TicketStatus.NEW);

        Ticket saved = ticketRepository.save(ticket);
        return TicketResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getTickets(
            TicketStatus status,
            TicketPriority priority,
            TicketCategory category,
            Long reporterId,
            Long assigneeId
    ) {
        return ticketRepository.findByFilters(status, priority, category, reporterId, assigneeId)
                .stream()
                .map(TicketResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = findTicketOrThrow(id);
        return TicketResponse.fromEntity(ticket);
    }

    public TicketResponse updateTicket(Long id, UpdateTicketRequest request) {
        Ticket ticket = findTicketOrThrow(id);
        ensureNotClosed(ticket);

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());

        return TicketResponse.fromEntity(ticketRepository.save(ticket));
    }

    public TicketResponse changeStatus(Long id, TicketStatus newStatus) {
        Ticket ticket = findTicketOrThrow(id);
        ensureNotClosed(ticket);

        ticket.setStatus(newStatus);
        return TicketResponse.fromEntity(ticketRepository.save(ticket));
    }

    public TicketResponse assignTicket(Long id, Long assigneeId) {
        Ticket ticket = findTicketOrThrow(id);
        ensureNotClosed(ticket);

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nie znaleziono użytkownika o ID: " + assigneeId
                ));

        if (assignee.getRole() != Role.IT_SPECIALIST && assignee.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Zgłoszenie może zostać przypisane tylko do Specjalisty IT lub Administratora"
            );
        }

        ticket.setAssignee(assignee);
        if (ticket.getStatus() == TicketStatus.NEW) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
        }

        return TicketResponse.fromEntity(ticketRepository.save(ticket));
    }

    public void deleteTicket(Long id) {
        Ticket ticket = findTicketOrThrow(id);
        ticketRepository.delete(ticket);
    }

    private Ticket findTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono zgłoszenia o ID: " + id));
    }

    private void ensureNotClosed(Ticket ticket) {
        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zgłoszenie jest zamknięte (CLOSED) i nie może być modyfikowane.");
        }
    }
}