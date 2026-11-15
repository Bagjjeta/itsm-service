package com.itsm.service.repository;

import com.itsm.service.model.Ticket;
import com.itsm.service.model.TicketCategory;
import com.itsm.service.model.TicketPriority;
import com.itsm.service.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            SELECT t FROM Ticket t
            WHERE (:status IS NULL OR t.status = :status)
              AND (:priority IS NULL OR t.priority = :priority)
              AND (:category IS NULL OR t.category = :category)
              AND (:reporterId IS NULL OR t.reporterId = :reporterId)
              AND (:assigneeId IS NULL OR t.assigneeId = :assigneeId)
            """)
    List<Ticket> findByFilters(
            @Param("status") TicketStatus status,
            @Param("priority") TicketPriority priority,
            @Param("category") TicketCategory category,
            @Param("reporterId") Long reporterId,
            @Param("assigneeId") Long assigneeId
    );
}