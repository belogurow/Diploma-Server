package ru.belogurow.socialnetworkserver.chat.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.chat.model.ChatRoom;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.familyCreators.ChatRoomFamilyCreator;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class ChatRoomServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void init() throws CustomException {
        user1 = UserFamilyCreator.createUser("user1");
        user2 = UserFamilyCreator.createUser("user2");
        user3 = UserFamilyCreator.createUser("user3");

        User resultSaveUser1 = userService.save(user1);
        User resultSaveUser2 = userService.save(user2);
        User resultSaveUser3 =userService.save(user3);

        assertEquals(user1.getUsername(), resultSaveUser1.getUsername());
        assertEquals(user2.getUsername(), resultSaveUser2.getUsername());
        assertEquals(user3.getUsername(), resultSaveUser3.getUsername());
    }

    @Test
    public void save() {
        ChatRoom chatRoom12 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());
        ChatRoom chatRoom23 = ChatRoomFamilyCreator.createChatRoom(user2.getId(), user3.getId());

        ChatRoom resultSaveChatRoom12 = chatRoomService.save(chatRoom12);
        ChatRoom resultSaveChatRoom23 = chatRoomService.save(chatRoom23);

        assertEquals(chatRoom12.getFirstUserId(), resultSaveChatRoom12.getFirstUserId());
        assertEquals(chatRoom12.getSecondUserId(), resultSaveChatRoom12.getSecondUserId());
        assertEquals(chatRoom23.getFirstUserId(), resultSaveChatRoom23.getFirstUserId());
        assertEquals(chatRoom23.getSecondUserId(), resultSaveChatRoom23.getSecondUserId());
        assertNotEquals(resultSaveChatRoom12, resultSaveChatRoom23);
    }

    @Test
    public void getById() {
        ChatRoom chatRoom12 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());

        ChatRoom resultSaveChatRoom12 = chatRoomService.save(chatRoom12);
        assertEquals(chatRoom12.getFirstUserId(), resultSaveChatRoom12.getFirstUserId());
        assertEquals(chatRoom12.getSecondUserId(), resultSaveChatRoom12.getSecondUserId());

        Optional<ChatRoom> resultGetById = chatRoomService.getById(resultSaveChatRoom12.getId());

        assertTrue(resultGetById.isPresent());
        assertEquals(chatRoom12.getFirstUserId(), resultGetById.get().getFirstUserId());
        assertEquals(chatRoom12.getSecondUserId(), resultGetById.get().getSecondUserId());

    }

    @Test
    public void findAll() {
        ChatRoom chatRoom12 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());
        ChatRoom chatRoom23 = ChatRoomFamilyCreator.createChatRoom(user2.getId(), user3.getId());

        ChatRoom resultSaveChatRoom12 = chatRoomService.save(chatRoom12);
        ChatRoom resultSaveChatRoom23 = chatRoomService.save(chatRoom23);

        List<ChatRoom> resultFindAll = chatRoomService.findAll();

        assertEquals(resultFindAll.size(), 2);
        assertTrue(resultFindAll.containsAll(Arrays.asList(chatRoom12, chatRoom23)));
    }

    @Test
    public void findAllByUserId() {
        ChatRoom chatRoom12 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());
        ChatRoom chatRoom23 = ChatRoomFamilyCreator.createChatRoom(user2.getId(), user3.getId());
        ChatRoom chatRoom13 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user3.getId());

        ChatRoom resultSaveChatRoom12 = chatRoomService.save(chatRoom12);
        ChatRoom resultSaveChatRoom23 = chatRoomService.save(chatRoom23);
        ChatRoom resultSaveChatRoom13 = chatRoomService.save(chatRoom13);

        List<ChatRoom> resultFindAllByUserId1 = chatRoomService.findAllByUserId(user1.getId());
        List<ChatRoom> resultFindAllByUserId2 = chatRoomService.findAllByUserId(user2.getId());
        List<ChatRoom> resultFindAllByUserId3 = chatRoomService.findAllByUserId(user3.getId());

        assertEquals(resultFindAllByUserId1.size(), 2);
        assertTrue(resultFindAllByUserId1.containsAll(Arrays.asList(chatRoom12, chatRoom13)));

        assertEquals(resultFindAllByUserId2.size(), 2);
        assertTrue(resultFindAllByUserId2.containsAll(Arrays.asList(chatRoom12, chatRoom23)));

        assertEquals(resultFindAllByUserId3.size(), 2);
        assertTrue(resultFindAllByUserId3.containsAll(Arrays.asList(chatRoom23, chatRoom13)));

    }

    @Test
    public void deleteAll() {
        ChatRoom chatRoom12 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user2.getId());
        ChatRoom chatRoom23 = ChatRoomFamilyCreator.createChatRoom(user2.getId(), user3.getId());
        ChatRoom chatRoom13 = ChatRoomFamilyCreator.createChatRoom(user1.getId(), user3.getId());

        ChatRoom resultSaveChatRoom12 = chatRoomService.save(chatRoom12);
        ChatRoom resultSaveChatRoom23 = chatRoomService.save(chatRoom23);
        ChatRoom resultSaveChatRoom13 = chatRoomService.save(chatRoom13);

        chatRoomService.deleteAll();

        List<ChatRoom> resultFindAll = chatRoomService.findAll();

        assertTrue(resultFindAll.isEmpty());
    }
}
