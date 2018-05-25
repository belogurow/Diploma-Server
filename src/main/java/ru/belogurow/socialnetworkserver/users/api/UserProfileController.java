package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.belogurow.socialnetworkserver.users.converter.ConvertUserProfile2UserProfileDto;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;

import java.util.stream.Collectors;

@RestController
public class UserProfileController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private UserProfileService userProfileService;
    private ConvertUserProfile2UserProfileDto convertUserProfile2UserProfileDto;

//    @RequestMapping(value = "/user-profile", method = RequestMethod.GET)
//    public ResponseEntity findUserProfileByUserId(@RequestParam("userId") UUID userId) {
//        LOGGER.info("findUserProfileByUserId({})", userId);
//
//        return userProfileService.findAllByUserId(userId)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

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
