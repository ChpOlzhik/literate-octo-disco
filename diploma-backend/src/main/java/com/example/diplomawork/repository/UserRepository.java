package com.example.diplomawork.repository;

import com.example.diplomawork.model.Role;
import com.example.diplomawork.model.Subject;
import com.example.diplomawork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findAllByRoleAndSubject(Role role, Subject subject);

    List<User> findAllByRole(Role role);

    Long countByRole(Role role);

    Optional<User> findByEmail(String email);
}
