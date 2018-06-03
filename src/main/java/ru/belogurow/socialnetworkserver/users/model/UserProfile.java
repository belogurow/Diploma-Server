package ru.belogurow.socialnetworkserver.users.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@Entity
@Table(name = "user_profile", schema = "public")
public class UserProfile implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "profession")
    private String profession;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "avatar_file_id", nullable = true)
    private UUID avatarFileId;

    @Column(name = "description")
    private String description;

    @Column(name = "birth_date", nullable = true)
    private Date birthDate;

    @Column(name = "role", nullable = false)
    private UserProfileRole role;

    public UserProfile() {
    }

    public UserProfileRole getRole() {
        return role;
    }

    public void setRole(UserProfileRole role) {
        this.role = role;
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

    public UUID getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(UUID avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("profession", profession)
                .append("userId", userId)
                .append("avatarFileId", avatarFileId)
                .append("description", description)
                .append("birthDate", birthDate)
                .append("role", role)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(profession, that.profession)
                .append(userId, that.userId)
                .append(avatarFileId, that.avatarFileId)
                .append(description, that.description)
                .append(birthDate, that.birthDate)
                .append(role, that.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(profession)
                .append(userId)
                .append(avatarFileId)
                .append(description)
                .append(birthDate)
                .append(role)
                .toHashCode();
    }
}
