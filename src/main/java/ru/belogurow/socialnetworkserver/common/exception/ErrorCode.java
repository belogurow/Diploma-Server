package ru.belogurow.socialnetworkserver.common.exception;

public enum ErrorCode {
    UNDEFINED("Undefined exception ", 0),
    LOGIN_EXISTS("This login is already exists", 1),
    LOGIN_INCORRECT("Login incorrect", 2),
    PASSWORD_INCORRECT("Password incorrect", 3);

    private final String description;
    private final int errorNumber;

    ErrorCode(final String description, final int errorNumber) {
        this.description = description;
        this.errorNumber = errorNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    @Override
    public String toString() {
        return "Code" + errorNumber + " - " + description;
    }
}
