package ru.belogurow.socialnetworkserver.chat.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.chat.model.ChatMessage;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.familyCreators.ChatMessageFamilyCreator;
import ru.belogurow.socialnetworkserver.common.familyCreators.ChatRoomFamilyCreator;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class ChatMessageServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;

    private User user1;
    private User user2;
    private ChatRoom chatRoom;

    @Before
    public void init() throws CustomException {
        user1 = UserFamilyCreator.createUser("user1");
        user2 = UserFamilyCreator.createUser("user2");

        User resultSaveUser1 = userService.save(user1);
        User resultSaveUser2 = userService.save(user2);

        assertEquals(user1.getUsername(), resultSaveUser1.getUsername());
        assertEquals(user2.getUsername(), resultSaveUser2.getUsername());

        chatRoom = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());

        ChatRoom resultSaveChatRoom = chatRoomService.save(chatRoom);

        assertEquals(chatRoom.getFirstUserId(), resultSaveChatRoom.getFirstUserId());
        assertEquals(chatRoom.getSecondUserId(), resultSaveChatRoom.getSecondUserId());
    }

    @Test
    public void save() {
        ChatMessage chatMessage1 = ChatMessageFamilyCreator.createChatMessage("test1", user1.getId(), chatRoom.getId());
        ChatMessage chatMessage2 = ChatMessageFamilyCreator.createChatMessage("test2", user2.getId(), chatRoom.getId());

        ChatMessage resultSaveChatMessage1 = chatMessageService.save(chatMessage1);
        ChatMessage resultSaveChatMessage2 = chatMessageService.save(chatMessage2);

        assertEquals(chatMessage1.getText(), resultSaveChatMessage1.getText());
        assertEquals(chatMessage1.getAuthorId(), resultSaveChatMessage1.getAuthorId());
        assertEquals(chatMessage2.getText(), resultSaveChatMessage2.getText());
        assertEquals(chatMessage2.getAuthorId(), resultSaveChatMessage2.getAuthorId());
    }

    @Test
    public void findById() {
        ChatMessage chatMessage1 = ChatMessageFamilyCreator.createChatMessage("test1", user1.getId(), chatRoom.getId());

        ChatMessage resultSaveChatMessage = chatMessageService.save(chatMessage1);
        Optional<ChatMessage> resultFindByIdChatMessage = chatMessageService.findById(resultSaveChatMessage.getId());

        assertTrue(resultFindByIdChatMessage.isPresent());
        assertEquals(resultSaveChatMessage.getText(), resultFindByIdChatMessage.get().getText());
        assertEquals(resultSaveChatMessage.getAuthorId(), resultFindByIdChatMessage.get().getAuthorId());
        assertEquals(resultSaveChatMessage.getChatRoomId(), resultFindByIdChatMessage.get().getChatRoomId());
    }

    @Test
    public void findAll() {
        ChatMessage chatMessage1 = ChatMessageFamilyCreator.createChatMessage("test1", user1.getId(), chatRoom.getId());
        ChatMessage chatMessage2 = ChatMessageFamilyCreator.createChatMessage("test2", user2.getId(), chatRoom.getId());
        ChatMessage chatMessage3 = ChatMessageFamilyCreator.createChatMessage("test3", user1.getId(), chatRoom.getId());

        ChatMessage resultSaveChatMessage1 = chatMessageService.save(chatMessage1);
        ChatMessage resultSaveChatMessage2 = chatMessageService.save(chatMessage2);
        ChatMessage resultSaveChatMessage3 = chatMessageService.save(chatMessage3);

        List<ChatMessage> resultFindAll = chatMessageService.findAll();

        assertEquals(resultFindAll.size(), 3);
        assertTrue(resultFindAll.containsAll(Arrays.asList(chatMessage1, chatMessage2, chatMessage3)));
    }

    @Test
    public void deleteAll() {
        ChatMessage chatMessage1 = ChatMessageFamilyCreator.createChatMessage("test1", user1.getId(), chatRoom.getId());
        ChatMessage chatMessage2 = ChatMessageFamilyCreator.createChatMessage("test2", user2.getId(), chatRoom.getId());
        ChatMessage chatMessage3 = ChatMessageFamilyCreator.createChatMessage("test3", user1.getId(), chatRoom.getId());

        ChatMessage resultSaveChatMessage1 = chatMessageService.save(chatMessage1);
        ChatMessage resultSaveChatMessage2 = chatMessageService.save(chatMessage2);
        ChatMessage resultSaveChatMessage3 = chatMessageService.save(chatMessage3);

        chatMessageService.deleteAll();
        List<ChatMessage> resultFindAll = chatMessageService.findAll();

        assertEquals(resultFindAll.size(), 0);
    }

    @Test
    public void getAllMessagesByChatRoomId() {
        ChatMessage chatMessage1 = ChatMessageFamilyCreator.createChatMessage("test1", user1.getId(), chatRoom.getId());
        ChatMessage chatMessage2 = ChatMessageFamilyCreator.createChatMessage("test2", user2.getId(), chatRoom.getId());
        ChatMessage chatMessage3 = ChatMessageFamilyCreator.createChatMessage("test3", user1.getId(), chatRoom.getId());

        ChatMessage resultSaveChatMessage1 = chatMessageService.save(chatMessage1);
        ChatMessage resultSaveChatMessage2 = chatMessageService.save(chatMessage2);
        ChatMessage resultSaveChatMessage3 = chatMessageService.save(chatMessage3);

        List<ChatMessage> resultFindAllByRoomId = chatMessageService.getAllMessagesByChatRoomId(chatRoom.getId());

        assertEquals(resultFindAllByRoomId.size(), 3);
        assertTrue(resultFindAllByRoomId.containsAll(Arrays.asList(chatMessage1, chatMessage2, chatMessage3)));
    }
}
