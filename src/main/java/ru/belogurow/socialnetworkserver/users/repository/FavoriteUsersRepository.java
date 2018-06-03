package ru.belogurow.socialnetworkserver.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;

import java.util.List;
import java.util.UUID;

public interface FavoriteUsersRepository extends JpaRepository<FavoriteUsers, UUID> {

    List<FavoriteUsers> findAllByFromUserId(UUID fromUserId);

    boolean existsFavoriteUsersByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);

    FavoriteUsers findByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);

    void deleteByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);
}
