package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;

import java.util.UUID;

public class ChatRoomFamilyCreator {

    public static ChatRoom createChatRoom(UUID firstUserId, UUID secondUserId) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setFirstUserId(firstUserId);
        chatRoom.setSecondUserId(secondUserId);

        return chatRoom;
    }
}
