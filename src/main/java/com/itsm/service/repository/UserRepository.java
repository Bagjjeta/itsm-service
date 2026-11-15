package com.itsm.service.repository;

import com.itsm.service.model.Role;
import com.itsm.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Poniższe metody zapewnia interfejs generyczny JpaRepository
// save(user), findById(id), findAll(), deleteById(id), delete(user), existsById(id), count()
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}