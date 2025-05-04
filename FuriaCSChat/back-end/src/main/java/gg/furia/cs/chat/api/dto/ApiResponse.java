package gg.furia.cs.chat.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import lombok.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String errorCode;
    private String errorMessage;
    @JsonIgnore
    private HttpStatusCode httpStatusCode;

    public static <T> ApiResponse<T> success(T data, HttpStatusCode status) {
        return new ApiResponse<>(
                true,
                data,
                null,
                null,
                status
        );
    }

    public static ApiResponse<Object> error(FuriaException exception) {
        return new ApiResponse<>(
                false,
                null,
                exception.getErrorCode().getMessageKey(),
                exception.getMessage(),
                exception.getErrorCode().getHttpStatusCode()
        );
    }

    public ResponseEntity<ApiResponse<T>> createResponseEntity() {
        return ResponseEntity.status(httpStatusCode).body(this);
    }
}
