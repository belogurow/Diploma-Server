package ru.belogurow.socialnetworkserver.users.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.belogurow.socialnetworkserver.users.model.UserRole;

import java.util.UUID;

public class UserDto {

    private UUID id;
    private String username;
    private String name;
    private String password;
    private UserRole userRole;
    private UserProfileDto userProfile;

    public UserDto() {
    }

    public UserDto(UUID id, String username, String name, String password, UserRole userRole, UserProfileDto userProfile) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.userProfile = userProfile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserProfileDto getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDto userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("name", name)
                .append("password", password)
                .append("userRole", userRole)
                .append("userProfile", userProfile)
                .toString();
    }
}
