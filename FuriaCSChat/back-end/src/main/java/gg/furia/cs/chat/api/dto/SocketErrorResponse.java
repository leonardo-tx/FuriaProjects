package gg.furia.cs.chat.api.dto;

import gg.furia.cs.chat.core.utils.exception.FuriaException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketErrorResponse {
    private String errorCode;
    private String message;

    public static SocketErrorResponse parse(FuriaException furiaException) {
        return new SocketErrorResponse(
                furiaException.getErrorCode().getMessageKey(),
                furiaException.getMessage()
        );
    }
}
