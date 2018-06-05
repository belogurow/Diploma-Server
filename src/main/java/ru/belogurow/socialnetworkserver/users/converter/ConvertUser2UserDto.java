package ru.belogurow.socialnetworkserver.users.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.users.dto.UserDto;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;

import java.util.Optional;

@Component
public class ConvertUser2UserDto {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ConvertUserProfile2UserProfileDto convertUserProfile2UserProfileDto;

    public UserDto convert(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setUserRole(user.getUserRole());

        Optional<UserProfile> userProfile = userProfileService.getByUserId(user.getId());
        if (userProfile.isPresent()) {
            userDto.setUserProfile(convertUserProfile2UserProfileDto.convert(userProfile.get()));
        } else {
            userDto.setUserProfile(null);
        }

        return userDto;
    }
}
