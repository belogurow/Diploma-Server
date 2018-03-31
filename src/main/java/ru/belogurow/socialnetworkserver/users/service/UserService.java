package ru.belogurow.socialnetworkserver.users.service;

import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.users.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public interface UserService {
    User login(User user) throws CustomException;

    User registration(User user) throws CustomException;

    User save(User user) throws CustomException;

    void update(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(UUID id);

    void deleteAll();

    boolean existsByUsername(String username);

    boolean existsById(UUID id);
}
