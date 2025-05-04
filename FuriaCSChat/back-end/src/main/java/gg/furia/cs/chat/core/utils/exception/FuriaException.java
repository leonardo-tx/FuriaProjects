package gg.furia.cs.chat.core.utils.exception;

import jakarta.annotation.Nullable;
import lombok.Getter;

import java.util.ResourceBundle;

@Getter
public class FuriaException extends Exception {
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("messages");

    private final FuriaErrorCode errorCode;

    public FuriaException(FuriaErrorCode errorCode) {
        super(MESSAGES.getString(errorCode.getMessageKey()));
        this.errorCode = errorCode;
    }

    public FuriaException(FuriaErrorCode errorCode, @Nullable Object... args) {
        super(String.format(MESSAGES.getString(errorCode.getMessageKey()), args));
        this.errorCode = errorCode;
    }
}
