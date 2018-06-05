package ru.belogurow.socialnetworkserver.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;
import ru.belogurow.socialnetworkserver.chat.repository.ChatMessageRepository;
import ru.belogurow.socialnetworkserver.chat.service.ChatMessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.saveAndFlush(chatMessage);
    }

    @Override
    public Optional<ChatMessage> findById(UUID chatMessageId) {
        return chatMessageRepository.findById(chatMessageId);
    }

    @Override
    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    @Override
    public void deleteAll() {
        chatMessageRepository.deleteAll();
    }

    @Override
    public ChatMessage getLastChatMessage(UUID chatRoomId) {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        List<ChatMessage> chatMessages = chatMessageRepository.getAllMessagesByChatRoomId(chatRoomId, PageRequest.of(0, 1, sort));

        if (chatMessages.isEmpty()) {
            return null;
        } else {
            return chatMessages.get(0);
        }
    }

    @Override
    public List<ChatMessage> getAllMessagesByChatRoomId(UUID chatRoomId) {
        return chatMessageRepository.getAllMessagesByChatRoomId(chatRoomId);
    }

    @Autowired
    public void setChatMessageRepository(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
}
