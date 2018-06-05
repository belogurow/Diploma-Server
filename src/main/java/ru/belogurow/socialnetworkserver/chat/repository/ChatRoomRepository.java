package ru.belogurow.socialnetworkserver.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query("SELECT r FROM ChatRoom r WHERE r.firstUserId = :userId or r.secondUserId = :userId")
    List<ChatRoom> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT r from ChatRoom r WHERE " +
            "r.firstUserId = :userId1 and r.secondUserId = :userId2 or " +
            "r.firstUserId = :userId2 and r.secondUserId = :userId1")
    Optional<ChatRoom> findByUserIds(@Param("userId1") UUID userId1, @Param("userId2") UUID userId2);
}
