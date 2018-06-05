package ru.belogurow.socialnetworkserver.userProfile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class UserProfileServiceTest {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;


    @Test
    public void save() throws CustomException {
        User user = UserFamilyCreator.createUser("save");

        User userSaveResult = userService.registration(user);

        assertNotNull(userSaveResult.getId());
        assertEquals(user.getUsername(), userSaveResult.getUsername());

        UserProfile userProfile = UserFamilyCreator.createUserProfile();

        UserProfile profileSaveResult = userProfileService.save(user.getId(), userProfile);

        assertNotNull(profileSaveResult.getId());
        assertEquals(userSaveResult.getId(), profileSaveResult.getUserId());
    }

    @Test
    public void saveUserNotFound() {
        UserProfile userProfile = UserFamilyCreator.createUserProfile();

        try {
            UserProfile profileSaveResult = userProfileService.save(UUID.randomUUID(), userProfile);
        } catch (CustomException e) {
            assertEquals(e.getErrorCode(), ErrorCode.USER_IS_NOT_FOUND);
        }
    }

    @Test
    public void findUserProfileByUserId() throws CustomException {
        User user = UserFamilyCreator.createUser("save");
        User userSaveResult = userService.registration(user);

        assertNotNull(userSaveResult.getId());

        UserProfile userProfile = UserFamilyCreator.createUserProfile();
        UserProfile profileSaveResult = userProfileService.save(user.getId(), userProfile);

        Optional<UserProfile> profileGetResult = userProfileService.getByUserId(userSaveResult.getId());
        assertTrue(profileGetResult.isPresent());
        assertEquals(profileSaveResult, profileGetResult.get());
    }

    @Test(expected = DataIntegrityViolationException.class)
    // MUST CONFLICT WITH UNIQUE (user_id)
    public void saveTest() throws CustomException {
        User user = UserFamilyCreator.createUser("saveTest");

        User userSaveResult = userService.registration(user);

        assertNotNull(userSaveResult.getId());
        assertEquals(user.getUsername(), userSaveResult.getUsername());

        UserProfile userProfile1 = UserFamilyCreator.createUserProfile();

        UserProfile profileSaveResult1 = userProfileService.save(user.getId(), userProfile1);

        assertNotNull(profileSaveResult1.getId());
        assertEquals(userSaveResult.getId(), profileSaveResult1.getUserId());

        UserProfile userProfile2 = UserFamilyCreator.createUserProfile();

        UserProfile profileSaveResult2 = userProfileService.save(user.getId(), userProfile2);
    }
}
