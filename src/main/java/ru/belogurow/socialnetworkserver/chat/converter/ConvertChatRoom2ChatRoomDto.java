package ru.belogurow.socialnetworkserver.chat.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.chat.dto.ChatRoomDto;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.chat.service.ChatMessageService;
import ru.belogurow.socialnetworkserver.users.service.UserService;

@Component
public class ConvertChatRoom2ChatRoomDto {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    public ChatRoomDto convert(ChatRoom chatRoom) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setId(chatRoom.getId());
        chatRoomDto.setFirstUser(userService.findById(chatRoom.getFirstUserId()).get());
        chatRoomDto.setSecondUser(userService.findById(chatRoom.getSecondUserId()).get());
        chatRoomDto.setLastChatMessage(chatMessageService.getLastChatMessage(chatRoom.getId()));

        return chatRoomDto;
    }
}
