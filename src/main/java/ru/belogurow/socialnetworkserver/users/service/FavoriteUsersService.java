package ru.belogurow.socialnetworkserver.users.service;

import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;

import java.util.List;
import java.util.UUID;

public interface FavoriteUsersService {

    FavoriteUsers save(FavoriteUsers users);

    List<FavoriteUsers> findAll();

    List<FavoriteUsers> findAllByFromUserId(UUID fromUserId);

    boolean isUserIsFavoriteForMe(UUID fromUserId, UUID toUserId);

    boolean deleteByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);
}
