package ru.belogurow.socialnetworkserver.users.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.users.dao.UserDatabaseService;
import ru.belogurow.socialnetworkserver.users.domain.User;
import ru.belogurow.socialnetworkserver.users.mybatis.mapper.UserMapper;

import java.util.List;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@Service
public class UserDatabaseServiceImpl implements UserDatabaseService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(User user) {
        return this.notExists(user.getLogin()) && userMapper.insert(user);
    }

    @Override
    public boolean update(User user) {
        return this.exists(user.getLogin()) && userMapper.update(user);
    }

    @Override
    public User findById(UUID id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean delete(User user) {
        return this.exists(user.getLogin()) && userMapper.delete(user);
    }

    @Override
    public boolean deleteAll() {
        return userMapper.deleteAll();
    }

    @Override
    public boolean exists(String login) {
        return userMapper.exists(login);
    }

    @Override
    public boolean notExists(String login) {
        return !userMapper.exists(login);
    }

}
