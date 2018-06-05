package ru.belogurow.socialnetworkserver.users.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author alexbelogurow
 */

public enum UserRole {
    /**
     * Admin role
     */
    ADMIN,

    /**
     * User role
     */
    USER;

    public List<UserRole> getStatuses() {
        return Arrays.asList(ADMIN, USER);
    }
}
