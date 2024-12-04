package com.library.auth_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered an error."),
    ACCESS_DENIED(403, HttpStatus.UNAUTHORIZED, "Access denied"),
    TOKEN_EXPIRED(402, HttpStatus.UNAUTHORIZED, "Token expired."),
    UNAUTHENTICATED(401, HttpStatus.BAD_REQUEST, "Token is invalid."),
    INVALID_CREDENTIALS(404, HttpStatus.UNAUTHORIZED, "Wrong password or username."),
    USER_ALREADY_EXISTED(405, HttpStatus.BAD_REQUEST, "User already existed."),
    USER_NOT_FOUND(406, HttpStatus.BAD_REQUEST, "User does not exist."),
    INVALID_INPUT(407, HttpStatus.BAD_REQUEST, "Wrong email or password."),
    POST_NOT_FOUND(414, HttpStatus.BAD_REQUEST, "Post does not exist."),
    COMMENT_NOT_FOUND(408, HttpStatus.BAD_REQUEST, "Comment does not exist."),
    ROLE_NOT_FOUND(409, HttpStatus.BAD_REQUEST, "Role does not exist."),
    EMAIL_SENDING_FAILED(410, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email."),
    REFRESH_TOKEN_INVALID(411, HttpStatus.UNAUTHORIZED, "Refresh token is invalid."),
    TOKEN_INVALID(412, HttpStatus.UNAUTHORIZED, "Token is invalid."),
    EMAIL_ALREADY_EXISTS(413, HttpStatus.CONFLICT, "Email already exists."),
    PHONE_NUMBER_ALREADY_EXISTS(415, HttpStatus.CONFLICT, "Phone number already exists.");

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
