package ru.belogurow.socialnetworkserver.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.chat.repository.ChatRoomRepository;
import ru.belogurow.socialnetworkserver.chat.service.ChatRoomService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.saveAndFlush(chatRoom);
    }

    @Override
    public Optional<ChatRoom> getById(UUID id) {
        return chatRoomRepository.findById(id);
    }

    @Override
    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Override
    public Optional<ChatRoom> findByUserIds(UUID userId1, UUID userId2) {
        return chatRoomRepository.findByUserIds(userId1, userId2);
    }

    @Override
    public List<ChatRoom> findAllByUserId(UUID userId) {
        return chatRoomRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteAll() {
        chatRoomRepository.deleteAll();
    }

    @Autowired
    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }
}
