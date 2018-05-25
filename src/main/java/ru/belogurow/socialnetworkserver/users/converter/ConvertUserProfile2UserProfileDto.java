package ru.belogurow.socialnetworkserver.users.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.chat.converter.ConvertFileEntity2FileEntityDto;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;
import ru.belogurow.socialnetworkserver.users.dto.UserProfileDto;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;

import java.util.Optional;

@Component
public class ConvertUserProfile2UserProfileDto {

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto;

    public UserProfileDto convert(UserProfile userProfile) {
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setId(userProfile.getId());
        userProfileDto.setProfession(userProfile.getProfession());
        userProfileDto.setUserId(userProfile.getUserId());

        Optional<FileEntity> fileEntity = fileEntityService.findById(userProfile.getAvatarFileId());
        if (fileEntity.isPresent()) {
            userProfileDto.setAvatarFile(convertFileEntity2FileEntityDto.convert(fileEntity.get()));
        } else {
            userProfileDto.setAvatarFile(null);
        }

        return userProfileDto;
    }
}
