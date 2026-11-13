package com.itsm.service.controller;

import com.itsm.service.dto.CreateUserRequest;
import com.itsm.service.dto.UserResponse;
import com.itsm.service.model.Role;
import com.itsm.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Zarządzanie użytkownikami systemu ITSM")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Utwórz nowego użytkownika")
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    @Operation(summary = "Pobierz listę użytkowników (opcjonalne filtrowanie po roli)")
    public List<UserResponse> getUsers(@RequestParam(required = false) Role role) {
        return userService.getUsers(role);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz szczegóły użytkownika po ID")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń użytkownika po ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
