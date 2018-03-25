package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.users.domain.User;
import ru.belogurow.socialnetworkserver.users.domain.UserStatus;

import java.util.UUID;

public class UserFamilyCreator {

    public static User createUser(String name) {
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setLogin(name);
        user.setName(name);
        user.setPassword(name);
        user.setUserStatus(UserStatus.USER);

        return user;
    }
}
