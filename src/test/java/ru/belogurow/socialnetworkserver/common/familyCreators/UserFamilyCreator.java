package ru.belogurow.socialnetworkserver.common.familyCreators;

import ru.belogurow.socialnetworkserver.users.model.UserAccount;
import ru.belogurow.socialnetworkserver.users.model.UserRole;

import java.util.UUID;

public class UserFamilyCreator {

    public static UserAccount createUser(String name) {
        UserAccount user = new UserAccount();

        user.setUsername(name);
        user.setName(name);
        user.setPassword(name);
        user.setUserRole(UserRole.USER);

        return user;
    }

    public static UserAccount createUserWithId(String name) {
        UserAccount user = createUser(name);
        user.setId(UUID.randomUUID());

        return user;
    }
}
