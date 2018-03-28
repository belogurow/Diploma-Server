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
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.UserAccount;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class UserAccountDatabaseServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountDatabaseService userAccountDatabaseService;

    @Test
    public void save() {
        UserAccount user = UserFamilyCreator.createUser("save");

        UserAccount resultUser = userAccountDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getUsername(), resultUser.getUsername());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveFail() {
        UserAccount user1 = UserFamilyCreator.createUser("insertFail");
        UserAccount user2 = UserFamilyCreator.createUser(user1.getUsername());

        userAccountDatabaseService.save(user1);
        userAccountDatabaseService.save(user2);
    }

    @Test
    public void update1() {
        UserAccount user = UserFamilyCreator.createUser("update1");

        UserAccount resultUser = userAccountDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getUsername(), resultUser.getUsername());

        user.setUsername("updateUser1");
        user.setName("updateUser1");

        userAccountDatabaseService.update(user);
    }

    @Test
    public void update2() {
        UserAccount user = UserFamilyCreator.createUser("update2");

        userAccountDatabaseService.update(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateFail() {
        UserAccount user1 = UserFamilyCreator.createUser("updateFail1");
        UserAccount userResult1 = userAccountDatabaseService.save(user1);

        assertTrue(userResult1.getId() != null);
        assertEquals(user1.getUsername(), userResult1.getUsername());

        UserAccount user2 = UserFamilyCreator.createUser("updateFail2");
        UserAccount userResult2 = userAccountDatabaseService.save(user2);

        assertTrue(userResult2.getId() != null);
        assertEquals(user2.getUsername(), userResult2.getUsername());

        userResult2.setUsername(userResult1.getUsername());
        userAccountDatabaseService.update(userResult2);
    }

    @Test
    public void findById() {
        UserAccount user = UserFamilyCreator.createUser("findUserById");

        UserAccount resultUser = userAccountDatabaseService.save(user);

        assertTrue(resultUser.getId() != null);
        assertEquals(user.getUsername(), resultUser.getUsername());

        Optional<UserAccount> resultFindUser = userAccountDatabaseService.findById(resultUser.getId());

        assertTrue(resultFindUser.isPresent());
        assertTrue(resultFindUser.get().getId() != null);
        assertEquals(user.getUsername(), resultFindUser.get().getUsername());
    }

    @Test
    public void findAll() {
        UserAccount user1 = UserFamilyCreator.createUser("findAll1");
        UserAccount user2 = UserFamilyCreator.createUser("findAll2");
        UserAccount user3 = UserFamilyCreator.createUser("findAll3");

        userAccountDatabaseService.save(user1);
        userAccountDatabaseService.save(user2);
        userAccountDatabaseService.save(user3);

        List<UserAccount> users = userAccountDatabaseService.findAll();

        assertTrue(userAccountDatabaseService.findAll().size() == 3);
        assertTrue(users.containsAll(Arrays.asList(user1, user2, user3)));

    }

    @Test
    public void delete() {
        UserAccount user = UserFamilyCreator.createUser("deleteUser");

        assertTrue(userAccountDatabaseService.findAll().isEmpty());

        userAccountDatabaseService.save(user);

        assertTrue(userAccountDatabaseService.findAll().size() == 1);

        userAccountDatabaseService.deleteById(user.getId());

        assertTrue(userAccountDatabaseService.findAll().isEmpty());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteFail() {
        UserAccount user = UserFamilyCreator.createUser("updateFail");

        assertTrue(userAccountDatabaseService.findAll().isEmpty());
        userAccountDatabaseService.deleteById(user.getId());
    }

    @Test
    public void deleteAll() {
        UserAccount user1 = UserFamilyCreator.createUser("findAll1");
        UserAccount user2 = UserFamilyCreator.createUser("findAll2");
        UserAccount user3 = UserFamilyCreator.createUser("findAll3");

        userAccountDatabaseService.save(user1);
        userAccountDatabaseService.save(user2);
        userAccountDatabaseService.save(user3);

        assertTrue(userAccountDatabaseService.findAll().size() == 3);

        userAccountDatabaseService.deleteAll();

        assertTrue(userAccountDatabaseService.findAll().isEmpty());
    }

    @Test
    public void existsByUsername() {
        UserAccount user = UserFamilyCreator.createUser("existsByUsername");

        userAccountDatabaseService.save(user);

        assertTrue(userAccountDatabaseService.existsByUsername(user.getUsername()));
        assertFalse(userAccountDatabaseService.existsByUsername("notExists"));
    }

    @Test
    public void existsById() {
        UserAccount user = UserFamilyCreator.createUser("existsById");

        userAccountDatabaseService.save(user);

        assertTrue(userAccountDatabaseService.existsById(user.getId()));
        assertFalse(userAccountDatabaseService.existsById(UUID.randomUUID()));
    }
}