package ru.belogurow.socialnetworkserver.chat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import ru.belogurow.socialnetworkserver.chat.model.Message;

import java.util.Date;
import java.util.UUID;

@RestController
public class ChatController {

    private static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message message(String textMessage) {
        LOGGER.info("message({})", textMessage);

        Message message = new Message();
        // TODO: 20.05.2018 SAVE TO DATASOURCE
        // TODO: 20.05.2018 MOVE TO SERVICE
        // TODO: 20.05.2018 SET AUTHOR ID
//         message.setAuthorId();
        message.setId(UUID.randomUUID().toString());
        message.setDate(new Date());
        message.setText(textMessage);

        return message;
    }
}
