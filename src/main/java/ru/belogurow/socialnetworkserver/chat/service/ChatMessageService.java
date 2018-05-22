package ru.belogurow.socialnetworkserver.chat.service;

import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatMessageService {

    ChatMessage save(ChatMessage chatMessage);

    Optional<ChatMessage> findById(UUID chatMessageId);

    List<ChatMessage> findAll();

    void deleteAll();

    List<ChatMessage> getAllMessagesByChatRoomId(UUID chatRoomId);
}
