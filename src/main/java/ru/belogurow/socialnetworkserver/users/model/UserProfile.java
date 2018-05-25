package ru.belogurow.socialnetworkserver.users.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

//    @OneToOne()
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "avatar_file_id", nullable = true)
    private UUID avatarFileId;

    public UserProfile() {
    }

    public UserProfile(String profession, UUID userId, UUID avatarFileId) {
        this.profession = profession;
        this.userId = userId;
        this.avatarFileId = avatarFileId;
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


//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(profession)
                .append(userId)
                .append(avatarFileId)
                .toHashCode();
    }
}
