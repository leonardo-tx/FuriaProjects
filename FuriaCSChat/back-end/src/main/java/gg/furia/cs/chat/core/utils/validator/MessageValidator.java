package gg.furia.cs.chat.core.utils.validator;

import gg.furia.cs.chat.core.entity.Message;
import gg.furia.cs.chat.core.utils.exception.FuriaErrorCode;
import gg.furia.cs.chat.core.utils.exception.FuriaException;

public final class MessageValidator {
    public static void validateFields(Message message) throws FuriaException {
        validateText(message.getText());
    }

    private static void validateText(String text) throws FuriaException {
        if (text == null || text.isBlank()) {
            throw new FuriaException(FuriaErrorCode.TEXT_EMPTY);
        }
        if (text.length() > Message.TEXT_MAX_LENGTH) {
            throw new FuriaException(FuriaErrorCode.TEXT_INVALID_LENGTH, Message.TEXT_MAX_LENGTH);
        }
    }
}
