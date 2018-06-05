package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.users.converter.ConvertUserProfile2UserProfileDto;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserProfileController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private UserProfileService userProfileService;
    private ConvertUserProfile2UserProfileDto convertUserProfile2UserProfileDto;

    @RequestMapping(value = "/user-profile", method = RequestMethod.POST)
    public ResponseEntity addUserProfile(@RequestBody UserProfile userProfile,
                                         @RequestParam(value = "userId") UUID userId) throws CustomException {
        LOGGER.info("addUserProfile({}, {})", userProfile, userId);

        return ResponseEntity.ok(convertUserProfile2UserProfileDto.convert(userProfileService.save(userId, userProfile)));
    }

    @RequestMapping(value = "/user-profile/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserProfileById(@PathVariable(value = "id") UUID id) {
        LOGGER.info("getUserProfileById({})", id);

        return userProfileService.getByUserId(id)
                .map(user -> ResponseEntity.ok(convertUserProfile2UserProfileDto.convert(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/user-profile", method = RequestMethod.GET)
    public ResponseEntity findAll() {
        LOGGER.info("findAll()");

        return ResponseEntity.ok(userProfileService.findAll()
                .parallelStream()
                .map(userProfile -> convertUserProfile2UserProfileDto.convert(userProfile))
                .collect(Collectors.toList()));
    }

    @Autowired
    public void setUserProfileService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Autowired
    public void setConvertUserProfile2UserProfileDto(ConvertUserProfile2UserProfileDto convertUserProfile2UserProfileDto) {
        this.convertUserProfile2UserProfileDto = convertUserProfile2UserProfileDto;
    }
}
