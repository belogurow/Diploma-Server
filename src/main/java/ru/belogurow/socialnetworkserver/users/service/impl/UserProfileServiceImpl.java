package ru.belogurow.socialnetworkserver.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.repository.UserProfileRepository;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserProfile save(UUID userId, UserProfile userProfile) throws CustomException {
        if (userService.existsById(userId)) {
            userProfile.setUserId(userId);
            return userProfileRepository.saveAndFlush(userProfile);
        } else {
            throw new CustomException(ErrorCode.USER_IS_NOT_FOUND);
        }
    }

    @Override
    public Optional<UserProfile> getByUserId(UUID userId) {
        return userProfileRepository.getByUserId(userId);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }
}
