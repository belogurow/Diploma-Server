package ru.belogurow.socialnetworkserver.chat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.belogurow.socialnetworkserver.chat.converter.ConvertChatRoom2ChatRoomDto;
import ru.belogurow.socialnetworkserver.chat.dto.ChatRoomDto;
import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.chat.service.ChatMessageService;
import ru.belogurow.socialnetworkserver.chat.service.ChatRoomService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    private static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private ConvertChatRoom2ChatRoomDto convertChatRoom2ChatRoomDto;
    private ChatRoomService chatRoomService;
    private ChatMessageService chatMessageService;

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public ResponseEntity createChat(@RequestBody ChatRoom chatRoom) {
        LOGGER.info("createChat({})", chatRoom);

        Optional<ChatRoom> chatRoomResult = chatRoomService.findByUserIds(chatRoom.getFirstUserId(), chatRoom.getSecondUserId());
        return chatRoomResult
                .map(chatRoom1 -> ResponseEntity.ok(convertChatRoom2ChatRoomDto.convert(chatRoom1)))
                .orElseGet(() -> ResponseEntity.ok(convertChatRoom2ChatRoomDto.convert(chatRoomService.save(chatRoom))));
    }

    @RequestMapping(value = "/chat/{userId}", method = RequestMethod.GET)
    public ResponseEntity getAllChatsByUserId(@PathVariable(value = "userId") UUID userId) {
        LOGGER.info("getAllChatsByUserId({})", userId);

        return ResponseEntity.ok(chatRoomService.findAllByUserId(userId)
                .parallelStream()
                .map(chatRoom -> convertChatRoom2ChatRoomDto.convert(chatRoom))
                .filter(chatRoomDto -> Objects.nonNull(chatRoomDto.getLastChatMessage()))
                .sorted(Comparator.comparing(r -> ((ChatRoomDto) r).getLastChatMessage().getDate()).reversed())
                .collect(Collectors.toList()));

    }

    @RequestMapping(value = "/chat/{chatId}/messages", method = RequestMethod.GET)
    public ResponseEntity getAllMessagesByChatId(@PathVariable("chatId") UUID chatId) {
        LOGGER.info("getAllMessagesByChatId({})", chatId);

        return ResponseEntity.ok(chatMessageService.getAllMessagesByChatRoomId(chatId));
    }

    @RequestMapping(value = "/chats", method = RequestMethod.GET)
    public ResponseEntity getAllChats() {
        LOGGER.info("getAllChats()");

        return ResponseEntity.ok(chatRoomService.findAll()
                .parallelStream()
                .map(chatRoom -> convertChatRoom2ChatRoomDto.convert(chatRoom))
                .filter(chatRoomDto -> Objects.nonNull(chatRoomDto.getLastChatMessage()))
                .sorted(Comparator.comparing(r -> ((ChatRoomDto) r).getLastChatMessage().getDate()).reversed())
                .collect(Collectors.toList()));
    }

    @Deprecated
    @MessageMapping("/chatRoom/{id}")
    @SendTo("/topic/messages/{id}")
    public ChatMessage message(@DestinationVariable("id") UUID id, String textMessage) {
        LOGGER.info("message({}, {})", id, textMessage);
        LOGGER.info("message({})", textMessage);

        ChatMessage message = new ChatMessage();
        message.setId(UUID.randomUUID());
        message.setDate(new Date());
        message.setText(textMessage + "DEPRECATED");
        return message;
    }

    @MessageMapping("/chatRoom/{chatId}/{authorId}")
    @SendTo("/topic/chatRoom/{chatId}/messages")
    public ChatMessage sendMessage(@DestinationVariable("chatId") String chatId,
                                   @DestinationVariable("authorId") String authorId,
                                   String textMessage) {
        LOGGER.info("sendMessage({}, {}, {})", chatId, authorId, textMessage);


        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthorId(UUID.fromString(authorId));
        chatMessage.setChatRoomId(UUID.fromString(chatId));
        chatMessage.setDate(new Date());
        chatMessage.setText(textMessage);

        return chatMessageService.save(chatMessage);
    }

    @Autowired
    public void setChatRoomService(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Autowired
    public void setChatMessageService(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @Autowired
    public void setConvertChatRoom2ChatRoomDto(ConvertChatRoom2ChatRoomDto convertChatRoom2ChatRoomDto) {
        this.convertChatRoom2ChatRoomDto = convertChatRoom2ChatRoomDto;
    }
}
