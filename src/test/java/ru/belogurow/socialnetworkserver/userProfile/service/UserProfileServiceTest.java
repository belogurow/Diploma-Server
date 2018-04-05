package ru.belogurow.socialnetworkserver.userProfile.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
//@Transactional
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

        UserProfile userProfile = UserFamilyCreator.createUserProfile(userSaveResult);

        UserProfile profileSaveResult = userProfileService.save(user.getId(), userProfile);

        assertNotNull(profileSaveResult.getId());
        assertEquals(userSaveResult, profileSaveResult.getUser());
    }

    @Test
    public void saveUserNotFound() {
        UserProfile userProfile = UserFamilyCreator.createUserProfile(null);

        try {
            UserProfile profileSaveResult = userProfileService.save(UUID.randomUUID(), userProfile);
        } catch (CustomException e) {
            assertEquals(e.getErrorCode(), ErrorCode.USER_IS_NOT_FOUND);
        }
    }

    @Test
    @Ignore
    // TODO: 05.04.2018
    // MUST CONFLICT WITH UNIQUE (id, user_id)
    public void saveTest() throws CustomException {
        User user = UserFamilyCreator.createUser("saveTest");

        User userSaveResult = userService.registration(user);

        assertNotNull(userSaveResult.getId());
        assertEquals(user.getUsername(), userSaveResult.getUsername());

        UserProfile userProfile1 = UserFamilyCreator.createUserProfile(userSaveResult);

        UserProfile profileSaveResult1 = userProfileService.save(user.getId(), userProfile1);

        assertNotNull(profileSaveResult1.getId());
        assertEquals(userSaveResult, profileSaveResult1.getUser());

        UserProfile userProfile2 = UserFamilyCreator.createUserProfile(userSaveResult);

        UserProfile profileSaveResult2 = userProfileService.save(user.getId(), userProfile2);
    }
}
