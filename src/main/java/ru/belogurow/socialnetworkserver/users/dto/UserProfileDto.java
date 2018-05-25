package ru.belogurow.socialnetworkserver.users.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.belogurow.socialnetworkserver.chat.dto.FileEntityDto;

import java.util.UUID;

public class UserProfileDto {
    private UUID id;
    private String profession;
    private UUID userId;
    private FileEntityDto avatarFile;

    public UserProfileDto() {
    }

    public UserProfileDto(UUID id, String profession, UUID userId, FileEntityDto avatarFile) {
        this.id = id;
        this.profession = profession;
        this.userId = userId;
        this.avatarFile = avatarFile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public FileEntityDto getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(FileEntityDto avatarFile) {
        this.avatarFile = avatarFile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("profession", profession)
                .append("userId", userId)
                .append("avatarFile", avatarFile)
                .toString();
    }
}
