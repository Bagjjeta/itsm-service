package com.itsm.service.controller;

import com.itsm.service.dto.CreateTicketRequest;
import com.itsm.service.dto.TicketResponse;
import com.itsm.service.dto.UpdateTicketRequest;
import com.itsm.service.model.TicketCategory;
import com.itsm.service.model.TicketPriority;
import com.itsm.service.model.TicketStatus;
import com.itsm.service.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Zarządzanie zgłoszeniami ITSM i obiegiem statusów")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Utwórz nowe zgłoszenie")
    public TicketResponse createTicket(@RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @GetMapping
    @Operation(summary = "Pobierz listę zgłoszeń z opcjonalnym filtrowaniem")
    public List<TicketResponse> getTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority,
            @RequestParam(required = false) TicketCategory category,
            @RequestParam(required = false) Long reporterId,
            @RequestParam(required = false) Long assigneeId
    ) {
        return ticketService.getTickets(status, priority, category, reporterId, assigneeId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz szczegóły zgłoszenia po ID")
    public TicketResponse getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edytuj podstawowe dane zgłoszenia")
    public TicketResponse updateTicket(@PathVariable Long id, @RequestBody UpdateTicketRequest request) {
        return ticketService.updateTicket(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Zmień status zgłoszenia w ramach Workflow")
    public TicketResponse changeStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        return ticketService.changeStatus(id, status);
    }

    @PatchMapping("/{id}/assign")
    @Operation(summary = "Przypisz realizatora IT do zgłoszenia")
    public TicketResponse assignTicket(@PathVariable Long id, @RequestParam Long assigneeId) {
        return ticketService.assignTicket(id, assigneeId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń zgłoszenie")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}