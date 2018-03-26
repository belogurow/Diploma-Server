package ru.belogurow.socialnetworkserver.users.api.controllers;

import org.springframework.http.ResponseEntity;
import ru.belogurow.socialnetworkserver.users.domain.User;

import java.util.List;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public interface UserController {
    ResponseEntity getUser(UUID id);

    List<User> getAll();

    ResponseEntity insertUser(User user);

    ResponseEntity updateUser(User user, UUID id);

    ResponseEntity deleteUser(UUID id);

    ResponseEntity deleteAll();
}
