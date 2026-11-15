package com.itsm.service.dto;

import com.itsm.service.model.Role;
import com.itsm.service.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String department,
        Role role,
        LocalDateTime createdAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDepartment(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}