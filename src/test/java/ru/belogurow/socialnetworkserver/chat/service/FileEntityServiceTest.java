package ru.belogurow.socialnetworkserver.chat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.familyCreators.FileEntityFamilyCreator;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class FileEntityServiceTest {

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private UserService userService;

    @Test
    public void saveJpg() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());

        FileEntity result = fileEntityService.save(fileEntity);

        assertEquals(fileEntity.getTitle(), result.getTitle());
        assertEquals(fileEntity.getFileType(), result.getFileType());
        assertTrue(Arrays.equals(fileEntity.getData(), result.getData()));
    }

    @Test
    public void saveStl() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("STL", user.getId());

        FileEntity result = fileEntityService.save(fileEntity);

        assertEquals(fileEntity.getTitle(), result.getTitle());
        assertEquals(fileEntity.getFileType(), result.getFileType());
        assertTrue(Arrays.equals(fileEntity.getData(), result.getData()));
    }

    @Test
    public void savePdf() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("PDF", user.getId());

        FileEntity result = fileEntityService.save(fileEntity);

        assertEquals(fileEntity.getTitle(), result.getTitle());
        assertEquals(fileEntity.getFileType(), result.getFileType());
        assertTrue(Arrays.equals(fileEntity.getData(), result.getData()));
    }

    @Test
    public void update() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());

        FileEntity saveResult = fileEntityService.save(fileEntity);
        assertEquals(fileEntity.getTitle(), saveResult.getTitle());

        saveResult.setTitle("NEW-JPG");
        fileEntityService.update(saveResult);

        Optional<FileEntity> getByIdResult = fileEntityService.findById(saveResult.getId());

        assertTrue(getByIdResult.isPresent());
        assertEquals(fileEntity.getTitle(), saveResult.getTitle());
        assertEquals(fileEntity.getTitle(), getByIdResult.get().getTitle());
        assertEquals(fileEntity.getFileType(), saveResult.getFileType());
        assertEquals(fileEntity.getFileType(), getByIdResult.get().getFileType());
        assertTrue(Arrays.equals(fileEntity.getData(), saveResult.getData()));
        assertTrue(Arrays.equals(fileEntity.getData(), getByIdResult.get().getData()));
    }

    @Test
    public void update1() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());

        fileEntityService.update(fileEntity);
    }

    @Test
    public void findById() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());

        FileEntity saveResult = fileEntityService.save(fileEntity);
        assertEquals(fileEntity.getTitle(), saveResult.getTitle());

        Optional<FileEntity> getByIdResult = fileEntityService.findById(saveResult.getId());
        assertTrue(getByIdResult.isPresent());
        assertEquals(fileEntity.getTitle(), getByIdResult.get().getTitle());
        assertEquals(fileEntity.getFileType(), saveResult.getFileType());
        assertTrue(Arrays.equals(fileEntity.getData(), getByIdResult.get().getData()));
    }

    @Test
    public void findAll() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity1 = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());
        FileEntity fileEntity2 = FileEntityFamilyCreator.createPdfFileEntity("PDF", user.getId());
        FileEntity fileEntity3 = FileEntityFamilyCreator.createStlFileEntity("STL", user.getId());

        fileEntityService.save(fileEntity1);
        fileEntityService.save(fileEntity2);
        fileEntityService.save(fileEntity3);

        List<FileEntity> result = fileEntityService.findAll();

        assertEquals(result.size(), 3);
        assertTrue(result.containsAll(Arrays.asList(fileEntity1, fileEntity2, fileEntity3)));
//        assertTrue(Arrays.equals(Arrays.asList(fileEntity1, fileEntity2, fileEntity3), result));

    }

    @Test
    public void getFileDataById() throws IOException, CustomException {
        User user = UserFamilyCreator.createUser("1");
        userService.save(user);

        FileEntity fileEntity = FileEntityFamilyCreator.createJpgFileEntity("JPG", user.getId());

        FileEntity saveResult = fileEntityService.save(fileEntity);
        assertEquals(fileEntity.getTitle(), saveResult.getTitle());

        byte[] result = fileEntityService.getFileDataById(saveResult.getId());

        assertTrue(Arrays.equals(fileEntity.getData(), result));
    }
}
