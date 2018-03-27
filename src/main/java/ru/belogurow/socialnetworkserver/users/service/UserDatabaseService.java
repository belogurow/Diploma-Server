package ru.belogurow.socialnetworkserver.users.service;

import ru.belogurow.socialnetworkserver.users.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public interface UserDatabaseService {
    User save(User user);

    void update(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    void deleteById(UUID id);

    void deleteAll();

    boolean existsByLogin(String login);

    boolean existsById(UUID id);
}
