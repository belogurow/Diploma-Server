package ru.belogurow.socialnetworkserver.chat.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.chat.dto.ChatRoomDto;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.chat.service.ChatMessageService;
import ru.belogurow.socialnetworkserver.users.converter.ConvertUser2UserDto;
import ru.belogurow.socialnetworkserver.users.service.UserService;

@Component
public class ConvertChatRoom2ChatRoomDto {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ConvertUser2UserDto convertUser2UserDto;

    public ChatRoomDto convert(ChatRoom chatRoom) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setId(chatRoom.getId());
        chatRoomDto.setFirstUser(convertUser2UserDto.convert(userService.findById(chatRoom.getFirstUserId()).get()));
        chatRoomDto.setSecondUser(convertUser2UserDto.convert(userService.findById(chatRoom.getSecondUserId()).get()));
        chatRoomDto.setLastChatMessage(chatMessageService.getLastChatMessage(chatRoom.getId()));

        return chatRoomDto;
    }
}
