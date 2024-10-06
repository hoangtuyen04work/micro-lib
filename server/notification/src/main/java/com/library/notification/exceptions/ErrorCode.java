package com.library.notification.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BAD_SERVER(400, HttpStatus.BAD_REQUEST, "SERVER ERROR"),
    USER_EXISTED(401, HttpStatus.BAD_REQUEST, "USER EXISTED"),
    USER_NOT_EXISTED(402, HttpStatus.BAD_REQUEST, "USER NOT EXISTED"),
    WRONG_PASSWORD_OR_USERNAME(403, HttpStatus.BAD_REQUEST, "WRONG PASSWORD OR USERNAME"),
    ACCESSDENIED(404, HttpStatus.FORBIDDEN, "ACCESS DENIED"),
    INVALID_INPUT(405, HttpStatus.BAD_REQUEST, "INVALID INPUT"),
    POST_NOT_EXISTED(406, HttpStatus.BAD_REQUEST, "POST NOT EXISTED"),
    NOT_AUTHENTICATED(407, HttpStatus.UNAUTHORIZED, "NOT AUTHENTICATED"),
    POST_NOT_FOUND(408, HttpStatus.NOT_FOUND, "POST NOT FOUND"),
    COMMENT_NOT_EXISTED(409, HttpStatus.BAD_REQUEST, "COMMENT NOT EXISTED"),
    ROLE_NOT_EXISTED(410, HttpStatus.BAD_REQUEST, "ROLE NOT EXISTED"),
    CANNOT_SEND_EMAIL(412, HttpStatus.BAD_REQUEST, "CANNOT SEND EMAIL"),
    REFRESH_TOKEN_INVALID(413, HttpStatus.BAD_REQUEST, "REFRESH TOKEN INVALID"),
    NOT_EXIST_BOOK(414, HttpStatus.BAD_REQUEST, "BOOK ID INVALID");


    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
