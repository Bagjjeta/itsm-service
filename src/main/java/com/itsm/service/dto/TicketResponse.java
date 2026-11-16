package com.itsm.service.dto;

import com.itsm.service.model.Ticket;
import com.itsm.service.model.TicketCategory;
import com.itsm.service.model.TicketPriority;
import com.itsm.service.model.TicketStatus;
import com.itsm.service.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private TicketCategory category;
    private TicketPriority priority;
    private TicketStatus status;

    private Long reporterId;
    private String reporterFullName;
    private String reporterEmail;

    private Long assigneeId;
    private String assigneeFullName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TicketResponse fromEntity(Ticket ticket) {
        User reporter = ticket.getReporter();
        User assignee = ticket.getAssignee();

        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .category(ticket.getCategory())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .reporterId(reporter != null ? reporter.getId() : null)
                .reporterFullName(reporter != null ? reporter.getFirstName() + " " + reporter.getLastName() : null)
                .reporterEmail(reporter != null ? reporter.getEmail() : null)
                .assigneeId(assignee != null ? assignee.getId() : null)
                .assigneeFullName(assignee != null ? assignee.getFirstName() + " " + assignee.getLastName() : null)
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}