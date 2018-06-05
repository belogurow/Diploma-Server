package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;

import java.util.Date;
import java.util.UUID;

public class ChatMessageFamilyCreator {

    public static ChatMessage createChatMessage(String text, UUID authorId, UUID chatRoomId) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setDate(new Date());
        chatMessage.setText(text);
        chatMessage.setAuthorId(authorId);
        chatMessage.setChatRoomId(chatRoomId);

        return chatMessage;
    }
}
