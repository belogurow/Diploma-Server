package ru.belogurow.socialnetworkserver.users.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.configs.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.users.domain.User;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class UserDatabaseServiceTest {

    @Autowired
    private UserDatabaseService userDatabaseService;

    @Test
    public void insert() {
        User user = UserFamilyCreator.createUser("insertUser");
        boolean insertResult = userDatabaseService.insert(user);

        assertTrue(insertResult);
    }


    @Test
    public void update() {
        User user = UserFamilyCreator.createUser("updateUser");
        assertTrue(userDatabaseService.insert(user));

        user.setName("updateUser1");
        assertTrue(userDatabaseService.update(user));
    }

    @Test
    public void findById() {
        User user = UserFamilyCreator.createUser("findUserById");
        assertTrue(userDatabaseService.insert(user));

        assertEquals(userDatabaseService.findById(user.getId()), user);
    }

    @Test
    public void findAll() {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        assertTrue(userDatabaseService.insert(user1));
        assertTrue(userDatabaseService.insert(user2));
        assertTrue(userDatabaseService.insert(user3));

        List<User> users = userDatabaseService.findAll();

        assertTrue(userDatabaseService.findAll().size() == 3);
        assertTrue(users.containsAll(Arrays.asList(user1, user2, user3)));

    }

    @Test
    public void delete() {
        User user = UserFamilyCreator.createUser("deleteUser");

        assertTrue(userDatabaseService.findAll().isEmpty());
        assertTrue(userDatabaseService.insert(user));
        assertTrue(userDatabaseService.findAll().size() == 1);

        assertTrue(userDatabaseService.delete(user));
        assertTrue(userDatabaseService.findAll().isEmpty());
    }

    @Test
    public void deleteAll() {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        assertTrue(userDatabaseService.insert(user1));
        assertTrue(userDatabaseService.insert(user2));
        assertTrue(userDatabaseService.insert(user3));

        assertTrue(userDatabaseService.findAll().size() == 3);
        assertTrue(userDatabaseService.deleteAll());
        assertTrue(userDatabaseService.findAll().isEmpty());
    }
}