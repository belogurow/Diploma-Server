package ru.belogurow.socialnetworkserver.users.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import ru.belogurow.socialnetworkserver.common.mybatis.DbMapper;
import ru.belogurow.socialnetworkserver.users.domain.User;

/**
 * @author alexbelogurow
 */

@Mapper
public interface UserMapper extends DbMapper<User> {
}
