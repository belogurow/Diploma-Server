package ru.belogurow.socialnetworkserver.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.users.model.User;

import java.util.UUID;

/**
 * @author alexbelogurow
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    User findByUsername(String username);
}
