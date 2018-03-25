package ru.belogurow.socialnetworkserver.users.dao;

import ru.belogurow.socialnetworkserver.common.mybatis.DbMapper;
import ru.belogurow.socialnetworkserver.users.domain.User;

/**
 * @author alexbelogurow
 */
public interface UserDatabaseService extends DbMapper<User> {
    boolean exists(String login);

    boolean notExists(String login);
}
