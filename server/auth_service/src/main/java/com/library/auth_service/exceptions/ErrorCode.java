package com.library.auth_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

//    BAD_SERVER(400, HttpStatus.BAD_REQUEST, "SERVER ERROR"),
//    UNAUTHENTICATED(401, HttpStatus.BAD_REQUEST, "UNAUTHENTICATED"),
//    USER_EXISTED(402, HttpStatus.BAD_REQUEST, "USER EXISTED"),
//    USER_NOT_EXISTED(403, HttpStatus.BAD_REQUEST, "USER NOT EXISTED"),
//    WRONG_PASSWORD_OR_USERNAME(404, HttpStatus.BAD_REQUEST, "WRONG PASSWORD OR USERNAME"),
//    ACCESSDENIED(405, HttpStatus.FORBIDDEN, "ACCESS DENIED"),
//    INVALID_INPUT(406, HttpStatus.BAD_REQUEST, "INVALID INPUT"),
//    POST_NOT_EXISTED(407, HttpStatus.BAD_REQUEST, "POST NOT EXISTED"),
//    COMMENT_NOT_EXISTED(410, HttpStatus.BAD_REQUEST, "COMMENT NOT EXISTED"),
//    ROLE_NOT_EXISTED(411, HttpStatus.BAD_REQUEST, "ROLE NOT EXISTED"),
//    CANNOT_SEND_EMAIL(412, HttpStatus.BAD_REQUEST, "CANNOT SEND EMAIL"),
//    USERNAME_EXISTED(414, HttpStatus.BAD_REQUEST, "USER NAME EXISTED"),
//    REFRESH_TOKEN_INVALID(413, HttpStatus.BAD_REQUEST, "REFRESH TOKEN INVALID"),
//    TOKEN_INVALID(414, HttpStatus.BAD_REQUEST, "TOKEN_INVALID"),
//    EMAIL_EXISTED(415, HttpStatus.BAD_REQUEST, "EMAIL EXISTED"),
//    PHONE_NUMBER(416, HttpStatus.BAD_REQUEST, "PHONE NUMBER EXISTED");



    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered an error."),
    UNAUTHENTICATED(401, HttpStatus.UNAUTHORIZED, "Authentication is required or token is invalid."),
    USER_ALREADY_EXISTS(402, HttpStatus.CONFLICT, "User already exists."),
    USER_NOT_FOUND(403, HttpStatus.NOT_FOUND, "User does not exist."),
    INVALID_CREDENTIALS(404, HttpStatus.UNAUTHORIZED, "Wrong password or username."),
    ACCESS_DENIED(405, HttpStatus.FORBIDDEN, "Access is denied."),
    INVALID_INPUT(406, HttpStatus.BAD_REQUEST, "Invalid input data."),
    POST_NOT_FOUND(407, HttpStatus.NOT_FOUND, "Post does not exist."),
    COMMENT_NOT_FOUND(408, HttpStatus.NOT_FOUND, "Comment does not exist."),
    ROLE_NOT_FOUND(409, HttpStatus.NOT_FOUND, "Role does not exist."),
    EMAIL_SENDING_FAILED(410, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email."),
    REFRESH_TOKEN_INVALID(411, HttpStatus.UNAUTHORIZED, "Refresh token is invalid."),
    TOKEN_INVALID(412, HttpStatus.UNAUTHORIZED, "Token is invalid."),
    EMAIL_ALREADY_EXISTS(413, HttpStatus.CONFLICT, "Email already exists."),
    PHONE_NUMBER_ALREADY_EXISTS(414, HttpStatus.CONFLICT, "Phone number already exists.");
    ;


    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
