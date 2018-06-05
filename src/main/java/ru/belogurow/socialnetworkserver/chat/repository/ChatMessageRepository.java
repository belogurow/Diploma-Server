package ru.belogurow.socialnetworkserver.chat.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoomId = :roomId")
    List<ChatMessage> getAllMessagesByChatRoomId(@Param(value = "roomId") UUID roomId);

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoomId = :roomId")
    List<ChatMessage> getAllMessagesByChatRoomId(@Param(value = "roomId") UUID roomId, Pageable pageable);
}
