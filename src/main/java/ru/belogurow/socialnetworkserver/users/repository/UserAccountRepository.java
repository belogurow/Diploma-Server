package ru.belogurow.socialnetworkserver.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.users.model.UserAccount;

import java.util.UUID;

/**
 * @author alexbelogurow
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    boolean existsByUsername(String username);

    UserAccount findByUsername(String username);
}
