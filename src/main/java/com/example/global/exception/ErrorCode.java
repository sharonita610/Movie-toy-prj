package com.example.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "INVALID REQUEST, CHECK THE FORM"),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "WRONG PASSWORD"),
    NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "USER WITH NO PERMISSION"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER NOT FOUND"),
    MAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"MAIL NOT FOUND"),
    DUPLICATE_MAIL(HttpStatus.CONFLICT, "MAIL DOES EXIST ALREADY"),
    SAME_RANK(HttpStatus.CONFLICT, "THE RANK IS THE AS BEFORE"),

    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE IN PROCESS");

    private final HttpStatus httpStatus;
    private final String message;

}
