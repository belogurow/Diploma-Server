package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.model.UserRole;

import java.util.UUID;

public class UserFamilyCreator {

    public static User createUser(String name) {
        User user = new User();

        user.setUsername(name);
        user.setName(name);
        user.setPassword(name);
        user.setUserRole(UserRole.USER);

        return user;
    }

    public static User createUserWithId(String name) {
        User user = createUser(name);
        user.setId(UUID.randomUUID());

        return user;
    }

    public static UserProfile createUserProfile(User user) {
        UserProfile userProfile = new UserProfile();

        userProfile.setProfession("someProfession");
        userProfile.setUser(user);

        return userProfile;
    }
}
