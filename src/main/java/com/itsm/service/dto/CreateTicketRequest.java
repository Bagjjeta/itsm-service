package com.itsm.service.dto;

import com.itsm.service.model.TicketCategory;
import com.itsm.service.model.TicketPriority;
import lombok.Data;

@Data
public class CreateTicketRequest {
    private String title;
    private String description;
    private TicketCategory category;
    private TicketPriority priority;
    private Long reporterId;
}