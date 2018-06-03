package ru.belogurow.socialnetworkserver.users.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.users.dto.FavoriteUsersDto;
import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;
import ru.belogurow.socialnetworkserver.users.service.UserService;

@Component
public class ConvertFavoriteUsers2FavoriteUsersDto {

    @Autowired
    private UserService userService;

    @Autowired
    private ConvertUser2UserDto convertUser2UserDto;

    public FavoriteUsersDto convert(FavoriteUsers favoriteUsers) {
        FavoriteUsersDto favoriteUsersDto = new FavoriteUsersDto();

        favoriteUsersDto.setId(favoriteUsers.getId());
        favoriteUsersDto.setFromUserId(convertUser2UserDto.convert(userService.findById(favoriteUsers.getFromUserId()).get()));
        favoriteUsersDto.setToUserId(convertUser2UserDto.convert(userService.findById(favoriteUsers.getToUserId()).get()));

        return favoriteUsersDto;
    }
}
