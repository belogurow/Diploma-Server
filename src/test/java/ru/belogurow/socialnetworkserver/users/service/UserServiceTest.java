package ru.belogurow.socialnetworkserver.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void registration() throws CustomException {
        User user = UserFamilyCreator.createUser("registration");

        User userResult = userService.registration(user);

        assertNotNull(userResult.getId());
        assertEquals(user.getUsername(), userResult.getUsername());
    }

    @Test
    public void registrationLoginExists() throws CustomException {
        User user = UserFamilyCreator.createUser("registration");

        User registration = userService.registration(user);

        assertNotNull(registration.getId());
        assertEquals(user.getUsername(), registration.getUsername());

        User user1 = UserFamilyCreator.createUser(user.getUsername());

        try {
            User registration1 = userService.registration(user);
        } catch (CustomException e){
            assertEquals(e.getErrorCode(), ErrorCode.LOGIN_EXISTS);
        }
    }

    @Test
    public void login() throws CustomException {
        User user = UserFamilyCreator.createUser("login");

        User registration = userService.registration(user);

        assertNotNull(registration.getId());
        assertEquals(user.getUsername(), registration.getUsername());

        User newUser = UserFamilyCreator.createUser("login");
        User login = userService.login(newUser);

        assertNotNull(login.getId());
        assertEquals(user.getUsername(), newUser.getUsername());
    }

    @Test
    public void loginIncorrectPassword() throws CustomException {
        User user = UserFamilyCreator.createUser("login");

        User registration = userService.registration(user);

        assertNotNull(registration.getId());
        assertEquals(user, registration);

        User newUser = UserFamilyCreator.createUser("login");
        newUser.setPassword("new password");

        try {
            User login = userService.login(newUser);
        } catch (CustomException e) {
            assertEquals(e.getErrorCode(), ErrorCode.PASSWORD_INCORRECT);
        }
    }

    @Test
    public void loginIncorrectLogin() throws CustomException {
        User user = UserFamilyCreator.createUser("login");

        User registration = userService.registration(user);

        assertNotNull(registration.getId());
        assertEquals(user, registration);

        User newUser = UserFamilyCreator.createUser("login123");

        try {
            User login = userService.login(newUser);
        } catch (CustomException e) {
            assertEquals(e.getErrorCode(), ErrorCode.LOGIN_INCORRECT);
        }
    }

    @Test
    public void save() throws CustomException {
        User user = UserFamilyCreator.createUser("save");

        User resultUser = userService.save(user);

        assertNotNull(resultUser.getId());
        assertEquals(user.getUsername(), resultUser.getUsername());
    }

    @Test(expected = CustomException.class)
    public void saveFail() throws CustomException {
        User user1 = UserFamilyCreator.createUser("insertFail");
        User user2 = UserFamilyCreator.createUser(user1.getUsername());

        userService.save(user1);
        userService.save(user2);
    }

    @Test
    public void update1() throws CustomException {
        User user = UserFamilyCreator.createUser("update1");

        User resultUser = userService.save(user);

        assertNotNull(resultUser.getId());
        assertEquals(user.getUsername(), resultUser.getUsername());

        user.setUsername("updateUser1");
        user.setName("updateUser1");

        userService.update(user);
    }

    @Test
    public void update2() {
        User user = UserFamilyCreator.createUser("update2");

        userService.update(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateFail() throws CustomException {
        User user1 = UserFamilyCreator.createUser("updateFail1");
        User userResult1 = userService.save(user1);

        assertNotNull(userResult1.getId());
        assertEquals(user1.getUsername(), userResult1.getUsername());

        User user2 = UserFamilyCreator.createUser("updateFail2");
        User userResult2 = userService.save(user2);

        assertNotNull(userResult2.getId());
        assertEquals(user2.getUsername(), userResult2.getUsername());

        userResult2.setUsername(userResult1.getUsername());
        userService.update(userResult2);
    }

    @Test
    public void findById() throws CustomException {
        User user = UserFamilyCreator.createUser("findUserById");

        User resultUser = userService.save(user);

        assertNotNull(resultUser.getId());
        assertEquals(user.getUsername(), resultUser.getUsername());

        Optional<User> resultFindUser = userService.findById(resultUser.getId());

        assertTrue(resultFindUser.isPresent());
        assertNotNull(resultFindUser.get().getId());
        assertEquals(user.getUsername(), resultFindUser.get().getUsername());
    }

    @Test
    public void findAll() throws CustomException {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        List<User> users = userService.findAll();

        assertEquals(3, users.size());
        assertTrue(users.containsAll(Arrays.asList(user1, user2, user3)));

    }

    @Test
    public void delete() throws CustomException {
        User user = UserFamilyCreator.createUser("deleteUser");

        assertTrue(userService.findAll().isEmpty());

        userService.save(user);

        assertEquals(1, userService.findAll().size());

        userService.deleteById(user.getId());

        assertTrue(userService.findAll().isEmpty());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteFail() {
        User user = UserFamilyCreator.createUser("updateFail");

        assertTrue(userService.findAll().isEmpty());
        userService.deleteById(user.getId());
    }

    @Test
    public void deleteAll() throws CustomException {
        User user1 = UserFamilyCreator.createUser("findAll1");
        User user2 = UserFamilyCreator.createUser("findAll2");
        User user3 = UserFamilyCreator.createUser("findAll3");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        assertEquals(3, userService.findAll().size());

        userService.deleteAll();

        assertTrue(userService.findAll().isEmpty());
    }

    @Test
    public void existsByUsername() throws CustomException {
        User user = UserFamilyCreator.createUser("existsByUsername");

        userService.save(user);

        assertTrue(userService.existsByUsername(user.getUsername()));
        assertFalse(userService.existsByUsername("notExists"));
    }

    @Test
    public void existsById() throws CustomException {
        User user = UserFamilyCreator.createUser("existsById");

        userService.save(user);

        assertTrue(userService.existsById(user.getId()));
        assertFalse(userService.existsById(UUID.randomUUID()));
    }
}