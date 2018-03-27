package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserStatus;

import java.util.UUID;

public class UserFamilyCreator {

    public static User createUser(String name) {
        User user = new User();

        user.setLogin(name);
        user.setName(name);
        user.setPassword(name);
        user.setUserStatus(UserStatus.USER);

        return user;
    }

    public static User createUserWithId(String name) {
        User user = createUser(name);
        user.setId(UUID.randomUUID());

        return user;
    }
}
