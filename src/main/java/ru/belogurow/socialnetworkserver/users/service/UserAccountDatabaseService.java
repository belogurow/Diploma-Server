package ru.belogurow.socialnetworkserver.users.service;

import ru.belogurow.socialnetworkserver.users.model.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public interface UserAccountDatabaseService {
    UserAccount save(UserAccount user);

    void update(UserAccount user);

    Optional<UserAccount> findById(UUID id);

    Optional<UserAccount> login(UserAccount user);

    Optional<UserAccount> findByUsername(String username);

    List<UserAccount> findAll();

    void deleteById(UUID id);

    void deleteAll();

    boolean existsByUsername(String username);

    boolean existsById(UUID id);
}
