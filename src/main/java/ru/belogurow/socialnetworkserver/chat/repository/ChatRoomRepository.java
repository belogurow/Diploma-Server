package ru.belogurow.socialnetworkserver.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query("SELECT r FROM ChatRoom r WHERE r.firstUserId = :userId or r.secondUserId = :userId")
    List<ChatRoom> findAllByUserId(@Param(value = "userId") UUID userId);
}
