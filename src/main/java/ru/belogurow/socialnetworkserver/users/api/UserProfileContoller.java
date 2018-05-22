package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;

@RestController
public class UserProfileContoller {

    private static Logger LOGGER = LoggerFactory.getLogger(UserProfileContoller.class);

    private UserProfileService userProfileService;

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

        return ResponseEntity.ok(userProfileService.findAll());
    }

    @Autowired
    public void setUserProfileService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
}
