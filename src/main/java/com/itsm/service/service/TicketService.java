package com.itsm.service.service;

import com.itsm.service.dto.CreateTicketRequest;
import com.itsm.service.dto.TicketResponse;
import com.itsm.service.dto.UpdateTicketRequest;
import com.itsm.service.model.Ticket;
import com.itsm.service.model.TicketCategory;
import com.itsm.service.model.TicketPriority;
import com.itsm.service.model.TicketStatus;
import com.itsm.service.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketResponse createTicket(CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());
        ticket.setReporterId(request.getReporterId());
        ticket.setStatus(TicketStatus.NEW);

        Ticket saved = ticketRepository.save(ticket);
        return TicketResponse.fromEntity(saved);
    }

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

        ticket.setAssigneeId(assigneeId);
        if (ticket.getStatus() == TicketStatus.NEW && assigneeId != null) {
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