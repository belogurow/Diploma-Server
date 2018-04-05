package ru.belogurow.socialnetworkserver.users.service;

import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileService {
    UserProfile save(UUID userId, UserProfile userProfile) throws CustomException;

    Optional<UserProfile> getByUserId(UUID userId);
}
