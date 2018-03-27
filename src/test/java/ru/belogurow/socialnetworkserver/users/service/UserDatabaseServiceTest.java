package ru.belogurow.socialnetworkserver.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDatabaseServiceTest {

    @Autowired
    private UserDatabaseService userDatabaseService;

    @Test
    public void save() {
        User user = UserFamilyCreator.createUser("save");

        User resultUser = userDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getLogin(), resultUser.getLogin());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveFail() {
        User user1 = UserFamilyCreator.createUser("insertFail");
        User user2 = UserFamilyCreator.createUser(user1.getLogin());

        userDatabaseService.save(user1);
        userDatabaseService.save(user2);
    }

    @Test
    public void update1() {
        User user = UserFamilyCreator.createUser("update1");

        User resultUser = userDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getLogin(), resultUser.getLogin());

        user.setLogin("updateUser1");
        user.setName("updateUser1");

        userDatabaseService.update(user);
    }

    @Test
    public void update2() {
        User user = UserFamilyCreator.createUser("update2");

        userDatabaseService.update(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateFail() {
        User user1 = UserFamilyCreator.createUser("updateFail1");
        User userResult1 = userDatabaseService.save(user1);

        assertTrue(userResult1.getId() != null);
        assertEquals(user1.getLogin(), userResult1.getLogin());

        User user2 = UserFamilyCreator.createUser("updateFail2");
        User userResult2 = userDatabaseService.save(user2);

        assertTrue(userResult2.getId() != null);
        assertEquals(user2.getLogin(), userResult2.getLogin());

        userResult2.setLogin(userResult1.getLogin());
        userDatabaseService.update(userResult2);
    }

    @Test
    public void findById() {
        User user = UserFamilyCreator.createUser("findUserById");

        User resultUser = userDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getLogin(), resultUser.getLogin());

        Optional<User> resultFindUser = userDatabaseService.findById(resultUser.getId());

        assertTrue(resultFindUser.isPresent());
        assertTrue(resultFindUser.get().getId() != null);
        assertEquals(user.getLogin(), resultFindUser.get().getLogin());
    }

    @Test
    public void findAll() {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        userDatabaseService.save(user1);
        userDatabaseService.save(user2);
        userDatabaseService.save(user3);

        List<User> users = userDatabaseService.findAll();

        assertTrue(userDatabaseService.findAll().size() == 3);
        assertTrue(users.containsAll(Arrays.asList(user1, user2, user3)));

    }

    @Test
    public void delete() {
        User user = UserFamilyCreator.createUser("deleteUser");

        assertTrue(userDatabaseService.findAll().isEmpty());

        userDatabaseService.save(user);

        assertTrue(userDatabaseService.findAll().size() == 1);

        userDatabaseService.deleteById(user.getId());

        assertTrue(userDatabaseService.findAll().isEmpty());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteFail() {
        User user = UserFamilyCreator.createUser("updateFail");

        assertTrue(userDatabaseService.findAll().isEmpty());
        userDatabaseService.deleteById(user.getId());
    }

    @Test
    public void deleteAll() {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        userDatabaseService.save(user1);
        userDatabaseService.save(user2);
        userDatabaseService.save(user3);

        assertTrue(userDatabaseService.findAll().size() == 3);

        userDatabaseService.deleteAll();

        assertTrue(userDatabaseService.findAll().isEmpty());
    }

    @Test
    public void existsByLogin() {
        User user = UserFamilyCreator.createUser("existsByLogin");

        userDatabaseService.save(user);

        assertTrue(userDatabaseService.existsByLogin(user.getLogin()));
        assertFalse(userDatabaseService.existsByLogin("notExists"));
    }

    @Test
    public void existsById() {
        User user = UserFamilyCreator.createUser("existsById");

        userDatabaseService.save(user);

        assertTrue(userDatabaseService.existsById(user.getId()));
        assertFalse(userDatabaseService.existsById(UUID.randomUUID()));
    }
}