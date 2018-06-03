package ru.belogurow.socialnetworkserver.chat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.belogurow.socialnetworkserver.chat.converter.ConvertFileEntity2FileEntityDto;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;
import ru.belogurow.socialnetworkserver.users.model.UserProfile;
import ru.belogurow.socialnetworkserver.users.service.UserProfileService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private FileEntityService fileEntityService;
    private UserProfileService userProfileService;
    private ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto;

    @Deprecated
    @RequestMapping(value = "/file/{userId}/avatar", method = RequestMethod.POST)
    public ResponseEntity uploadUserAvatar(@RequestBody MultipartFile file,
                                           @RequestPart("fileEntity") FileEntity fileEntity,
                                           @PathVariable("userId") UUID userId) throws CustomException, IOException {
        LOGGER.info("uploadUserAvatar({}, {}, {})", file.getName(), fileEntity, userId);

        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_IS_EMPTY);
        }


        fileEntity.setData(file.getBytes());
        fileEntity.setUpdateTime(new Date());

        FileEntity fileEntityResult = fileEntityService.save(fileEntity);

        // save new avatar to user_profile
        Optional<UserProfile> userProfile = userProfileService.getByUserId(userId);
        if (userProfile.isPresent()) {
            userProfile.get().setAvatarFileId(fileEntityResult.getId());
            userProfileService.save(userId, userProfile.get());
        } else {
            UserProfile newUseProfile = new UserProfile();

            newUseProfile.setUserId(userId);
            newUseProfile.setAvatarFileId(fileEntityResult.getId());

            userProfileService.save(userId, newUseProfile);
        }

        return ResponseEntity.ok(convertFileEntity2FileEntityDto.convert(fileEntityResult));
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestBody MultipartFile file,
                                     @RequestPart("fileEntity") FileEntity fileEntity,
                                     @RequestParam(value = "isAvatar", required = false) boolean isAvatar) throws CustomException, IOException {
        LOGGER.info("uploadFile({}, {}, {})", file.getName(), fileEntity, isAvatar);

        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_IS_EMPTY);
        }

        fileEntity.setData(file.getBytes());
        fileEntity.setUpdateTime(new Date());


        FileEntity fileEntityResult = fileEntityService.save(fileEntity);

        if (isAvatar) {
            // save new avatar to user_profile
            UUID authorId = fileEntity.getAuthorId();
            Optional<UserProfile> userProfile = userProfileService.getByUserId(authorId);
            if (userProfile.isPresent()) {
                userProfile.get().setAvatarFileId(fileEntityResult.getId());
                userProfileService.save(authorId, userProfile.get());
            } else {
                UserProfile newUseProfile = new UserProfile();

                newUseProfile.setUserId(authorId);
                newUseProfile.setAvatarFileId(fileEntityResult.getId());

                userProfileService.save(authorId, newUseProfile);
            }
        }

        return ResponseEntity.ok(convertFileEntity2FileEntityDto.convert(fileEntityResult));
    }

//    @RequestMapping(value = "/files", method = RequestMethod.GET)
//    public ResponseEntity getAllFilesByUserId(@RequestParam("userId") UUID userId) {
//        LOGGER.info("getAllFilesByUserId({})", userId);
//
//        return ResponseEntity.ok(fileEntityService
//        .)
//    }

    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
    public ResponseEntity getFile(@PathVariable("fileId") UUID fileId) throws CustomException {
        LOGGER.info("getFile({})", fileId);

        Optional<FileEntity> file = fileEntityService.findById(fileId);

        if (file.isPresent()) {
            FileEntity fileEntity = file.get();
            byte[] fileData = fileEntity.getData();

            return ResponseEntity.ok()
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header("Content-Disposition", "inline; filename=" + fileEntity.getTitle() + "." + fileEntity.getFileType().toString().toLowerCase())
                    .body(fileData);
        } else {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity getAllFiles(@RequestParam(value = "userId", required = false) UUID userId) {
        LOGGER.info("getAllFiles({})", userId);

        if (userId == null) {
            return ResponseEntity.ok(fileEntityService
                    .findAll()
                    .parallelStream()
                    .map(f -> convertFileEntity2FileEntityDto.convert(f))
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok(fileEntityService
                    .findAllByAuthorId(userId)
                    .parallelStream()
                    .map(f -> convertFileEntity2FileEntityDto.convert(f))
                    .collect(Collectors.toList()));
        }
    }

    @Autowired
    public void setFileEntityService(FileEntityService fileEntityService) {
        this.fileEntityService = fileEntityService;
    }

    @Autowired
    public void setConvertFileEntity2FileEntityDto(ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto) {
        this.convertFileEntity2FileEntityDto = convertFileEntity2FileEntityDto;
    }

    @Autowired
    public void setUserProfileService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
}
