package ru.belogurow.socialnetworkserver.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;

import java.util.List;
import java.util.UUID;

public interface FavoriteUsersRepository extends JpaRepository<FavoriteUsers, UUID> {

    List<FavoriteUsers> findAllByFromUserId(UUID fromUserId);

    boolean existsFavoriteUsersByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);

    FavoriteUsers findByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavoriteUsers m WHERE m.fromUserId = :fromUserId AND m.toUserId = :toUserId")
    void deleteByFromUserIdAndToUserId(@Param(value = "fromUserId") UUID fromUserId,
                                       @Param(value = "toUserId") UUID toUserId);
}
