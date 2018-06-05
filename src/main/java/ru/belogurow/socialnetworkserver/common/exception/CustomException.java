package ru.belogurow.socialnetworkserver.common.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomException extends Exception{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.errorCode = code;
    }

    public CustomException(String message, ErrorCode code) {
        super(message);
        this.errorCode = code;
    }
    public CustomException(Throwable cause, ErrorCode code) {
        super(cause);
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("errorCode", errorCode)
                .toString();
    }
}
