package com.itsm.service.dto;

import com.itsm.service.model.Role;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String department,
        Role role
) {
}
