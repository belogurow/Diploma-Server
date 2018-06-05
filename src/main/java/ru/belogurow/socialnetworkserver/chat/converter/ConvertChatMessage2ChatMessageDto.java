package ru.belogurow.socialnetworkserver.chat.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.chat.dto.ChatMessageDto;
import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;

@Component
public class ConvertChatMessage2ChatMessageDto {

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto;

    public ChatMessageDto convert(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();

        chatMessageDto.setId(chatMessage.getId());
        chatMessageDto.setAuthorId(chatMessage.getAuthorId());
        chatMessageDto.setChatRoomId(chatMessage.getChatRoomId());
        chatMessageDto.setDate(chatMessage.getDate());
        chatMessageDto.setText(chatMessage.getText());

        if (chatMessage.getFileEntityId() != null) {
            chatMessageDto.setFileEntity(
                    convertFileEntity2FileEntityDto.convert(
                            fileEntityService.findById(chatMessage.getFileEntityId()).get()));
        } else {
            chatMessageDto.setFileEntity(null);
        }

        return chatMessageDto;
    }
}
