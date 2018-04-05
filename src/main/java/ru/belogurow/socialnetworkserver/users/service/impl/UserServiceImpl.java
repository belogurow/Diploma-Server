package ru.belogurow.socialnetworkserver.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserRole;
import ru.belogurow.socialnetworkserver.users.repository.UserRepository;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(User user) throws CustomException {
        Optional<User> userFromDB = this.findByUsername(user.getUsername());

        // TODO: 30.03.2018 VALIDATE FIELDS?

        if (userFromDB.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), userFromDB.get().getPassword())) {
//            if (user.getPassword().contentEquals(userFromDB.get().getPassword())) {
                // Login Success
                return userFromDB.get();

            } else {
                // Incorrect password
                throw new CustomException(ErrorCode.PASSWORD_INCORRECT);
            }

        } else {
            // Incorrect login
            throw new CustomException(ErrorCode.LOGIN_INCORRECT);
        }
    }

    @Override
    public User registration(User user) throws CustomException {
        // TODO: 30.03.2018 VALIDATE FIELDS?

        return this.save(user);
    }

    @Override
    public User save(User user) throws CustomException {
        Optional<User> userFromDB = this.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            throw new CustomException(ErrorCode.LOGIN_EXISTS);
        } else {
            // Create new user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(UserRole.USER);
            return userRepository.save(user);
        }
    }

    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }


    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (this.existsByUsername(username)) {
            return Optional.of(userRepository.findByUsername(username));
        }

        return Optional.empty();
    }
}
