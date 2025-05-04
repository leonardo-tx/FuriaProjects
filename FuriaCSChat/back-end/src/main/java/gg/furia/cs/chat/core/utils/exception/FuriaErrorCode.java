package gg.furia.cs.chat.core.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum FuriaErrorCode {
    TEXT_EMPTY("text.empty", HttpStatus.BAD_REQUEST),
    TEXT_INVALID_LENGTH("text.invalid.length", HttpStatus.BAD_REQUEST),
    USER_NAME_EMPTY("user.name.empty", HttpStatus.BAD_REQUEST),
    USER_NAME_INVALID_LENGTH("user.name.invalid.length", HttpStatus.BAD_REQUEST),
    USER_NAME_INVALID_CHARACTER("user.name.invalid.character", HttpStatus.BAD_REQUEST),
    USER_NAME_INVALID("user.name.invalid", HttpStatus.BAD_REQUEST),
    USER_NAME_ALREADY_EXISTS("user.name.already.exists", HttpStatus.BAD_REQUEST),
    EMAIL_EMPTY("email.empty", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID_LENGTH("email.invalid.length", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID("email.invalid", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("email.already.exists", HttpStatus.BAD_REQUEST),
    PASSWORD_EMPTY("password.empty", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_LENGTH("password.invalid.length", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_CHARACTER("password.invalid.character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_BYTES("password.invalid.bytes", HttpStatus.BAD_REQUEST),
    NAME_EMPTY("name.empty", HttpStatus.BAD_REQUEST),
    NAME_INVALID_LENGTH("name.invalid.length", HttpStatus.BAD_REQUEST),
    NAME_INVALID("name.invalid", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("user.not.found", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_EXPIRED("refresh.token.expired", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID("refresh.token.invalid", HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_EXPIRED("access.token.expired", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID("access.token.invalid", HttpStatus.BAD_REQUEST),
    LOGIN_UNAUTHORIZED("login.unauthorized", HttpStatus.BAD_REQUEST),
    LOGIN_ALREADY_LOGGED("login.already.logged", HttpStatus.BAD_REQUEST),
    COMMON_UNAUTHORIZED("common.unauthorized", HttpStatus.UNAUTHORIZED),
    INTERNAL("internal", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("bad.request", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("method.not.allowed", HttpStatus.METHOD_NOT_ALLOWED),
    ENDPOINT_NOT_FOUND("endpoint.not.found", HttpStatus.NOT_FOUND);

    private final String messageKey;
    private final HttpStatusCode httpStatusCode;

    FuriaErrorCode(String messageKey, HttpStatusCode httpStatusCode) {
        this.messageKey = messageKey;
        this.httpStatusCode = httpStatusCode;
    }
}
