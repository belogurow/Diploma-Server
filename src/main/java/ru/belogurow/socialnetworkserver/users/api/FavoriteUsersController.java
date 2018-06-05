package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.belogurow.socialnetworkserver.users.converter.ConvertFavoriteUsers2FavoriteUsersDto;
import ru.belogurow.socialnetworkserver.users.model.FavoriteUsers;
import ru.belogurow.socialnetworkserver.users.service.FavoriteUsersService;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class FavoriteUsersController {

    private static Logger LOGGER = LoggerFactory.getLogger(FavoriteUsersController.class);

    private FavoriteUsersService favoriteUsersService;
    private ConvertFavoriteUsers2FavoriteUsersDto convertFavoriteUsers2FavoriteUsersDto;

    @RequestMapping(value = "/favorite-users", method = POST)
    public ResponseEntity addNewFavoriteUser(@RequestBody FavoriteUsers favoriteUsers) {
        LOGGER.info("addNewFavoriteUser({})", favoriteUsers.toString());

        FavoriteUsers result = favoriteUsersService.save(favoriteUsers);
        return ResponseEntity.ok(convertFavoriteUsers2FavoriteUsersDto.convert(result));
    }

    @RequestMapping(value = "/favorite-users", method = GET)
    public ResponseEntity getAllFavoriteUsersByUserId(@RequestParam(value = "fromUserId") UUID fromUserId) {
        LOGGER.info("getAllFavoriteUsersByUserId({})", fromUserId);

        return ResponseEntity.ok(favoriteUsersService.findAllByFromUserId(fromUserId)
                .parallelStream()
                .map(u -> convertFavoriteUsers2FavoriteUsersDto.convert(u))
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/favorite-users/is-favorite", method = GET)
    public ResponseEntity isFromUserIdHasFavToUserId(@RequestParam("fromUserId") UUID fromUserId,
                                                     @RequestParam("toUserId") UUID toUserId) {
        LOGGER.info("isFromUserIdHasFavToUserId({}, {})", fromUserId, toUserId);

        return ResponseEntity.ok(favoriteUsersService.isUserIsFavoriteForMe(fromUserId, toUserId));
    }

    @RequestMapping(value = "/favorite-users", method = DELETE)
    public ResponseEntity deleteFromUserIdFavForToUserId(@RequestParam("fromUserId") UUID fromUserId,
                                                         @RequestParam("toUserId") UUID toUserId) {
        LOGGER.info("deleteFromUserIdFavForToUserId({}, {})", fromUserId, toUserId);

        return ResponseEntity.ok(favoriteUsersService.deleteByFromUserIdAndToUserId(fromUserId, toUserId));
    }

    @Autowired
    public void setConvertFavoriteUsers2FavoriteUsersDto(ConvertFavoriteUsers2FavoriteUsersDto convertFavoriteUsers2FavoriteUsersDto) {
        this.convertFavoriteUsers2FavoriteUsersDto = convertFavoriteUsers2FavoriteUsersDto;
    }

    @Autowired
    public void setFavoriteUsersService(FavoriteUsersService favoriteUsersService) {
        this.favoriteUsersService = favoriteUsersService;
    }
}
