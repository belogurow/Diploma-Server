package ru.belogurow.socialnetworkserver.chat.service;

import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomService {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> getById(UUID id);

    List<ChatRoom> findAll();

    List<ChatRoom> findAllByUserId(UUID userId);

    void deleteAll();

}
