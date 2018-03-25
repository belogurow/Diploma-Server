package ru.belogurow.socialnetworkserver.users.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.users.dao.UserDatabaseService;
import ru.belogurow.socialnetworkserver.users.domain.User;
import ru.belogurow.socialnetworkserver.users.mybatis.mapper.UserMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author alexbelogurow
 */

@Service
public class UserDatabaseServiceImpl implements UserDatabaseService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User findById(Serializable id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean delete(User user) {
        return userMapper.delete(user);
    }

    @Override
    public boolean deleteAll() {
        return userMapper.deleteAll();
    }
}
