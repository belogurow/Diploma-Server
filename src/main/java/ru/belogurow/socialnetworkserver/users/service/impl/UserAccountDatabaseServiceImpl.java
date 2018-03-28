package ru.belogurow.socialnetworkserver.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.users.model.UserAccount;
import ru.belogurow.socialnetworkserver.users.model.UserStatus;
import ru.belogurow.socialnetworkserver.users.repository.UserAccountRepository;
import ru.belogurow.socialnetworkserver.users.service.UserAccountDatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@Service
public class UserAccountDatabaseServiceImpl implements UserAccountDatabaseService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount save(UserAccount user) {
        return userAccountRepository.saveAndFlush(user);
    }

    @Override
    public void update(UserAccount user) {
        userAccountRepository.saveAndFlush(user);
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public Optional<UserAccount> login(UserAccount user) {
        if (userAccountRepository.existsByUsername(user.getUsername())) {
            return Optional.empty();
        }

        user.setUserStatus(UserStatus.ONLINE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(this.save(user));
    }

    @Override
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userAccountRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(UUID id) {
        return userAccountRepository.existsById(id);
    }


    @Override
    public void deleteById(UUID id) {
        userAccountRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userAccountRepository.deleteAll();
    }

}
