package ru.belogurow.socialnetworkserver.users.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@Entity
@Table(name = "user_account", schema = "public")
public class UserAccount implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "user_status")
    private UserStatus userStatus;

    public UserAccount() {
    }

    public UserAccount(UUID id, String username, String name, String password, UserRole userRole, UserStatus userStatus) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("username", username)
                .append("name", name)
                .append("password", password)
                .append("userRole", userRole)
                .append("userStatus", userStatus)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(username, that.username)
                .append(name, that.name)
                .append(password, that.password)
                .append(userRole, that.userRole)
                .append(userStatus, that.userStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(name)
                .append(password)
                .append(userRole)
                .append(userStatus)
                .toHashCode();
    }
}
