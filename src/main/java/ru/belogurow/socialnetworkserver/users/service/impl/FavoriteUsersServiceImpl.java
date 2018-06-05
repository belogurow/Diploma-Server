package ru.belogurow.socialnetworkserver.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;
import ru.belogurow.socialnetworkserver.users.repository.FavoriteUsersRepository;
import ru.belogurow.socialnetworkserver.users.service.FavoriteUsersService;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteUsersServiceImpl implements FavoriteUsersService {

    @Autowired
    private FavoriteUsersRepository favoriteUsersRepository;

    @Override
    public FavoriteUsers save(FavoriteUsers users) {
        if (!favoriteUsersRepository.existsFavoriteUsersByFromUserIdAndToUserId(users.getFromUserId(), users.getToUserId())) {
            return favoriteUsersRepository.saveAndFlush(users);
        } else {
            return favoriteUsersRepository.findByFromUserIdAndToUserId(users.getFromUserId(), users.getToUserId());
        }
    }

    @Override
    public List<FavoriteUsers> findAll() {
        return favoriteUsersRepository.findAll();
    }

    @Override
    public List<FavoriteUsers> findAllByFromUserId(UUID fromUserId) {
        return favoriteUsersRepository.findAllByFromUserId(fromUserId);
    }

    @Override
    public boolean isUserIsFavoriteForMe(UUID fromUserId, UUID toUserId) {
        return favoriteUsersRepository.existsFavoriteUsersByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    @Override
    public boolean deleteByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId) {
        favoriteUsersRepository.deleteByFromUserIdAndToUserId(fromUserId, toUserId);
        return !favoriteUsersRepository.existsFavoriteUsersByFromUserIdAndToUserId(fromUserId, toUserId);
    }
}
