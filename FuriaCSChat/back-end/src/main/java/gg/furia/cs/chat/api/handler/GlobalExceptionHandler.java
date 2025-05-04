package gg.furia.cs.chat.api.handler;

import gg.furia.cs.chat.api.dto.ApiResponse;
import gg.furia.cs.chat.core.utils.exception.FuriaErrorCode;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> globalExceptionHandler(
            Exception e,
            WebRequest request
    ) {
        logger.error("Error:", e);
        FuriaException exception = new FuriaException(FuriaErrorCode.INTERNAL);
        return ApiResponse.error(exception).createResponseEntity();
    }

    @ExceptionHandler(FuriaException.class)
    public ResponseEntity<ApiResponse<Object>> globalFuriaExceptionHandler(
            FuriaException e,
            WebRequest request
    ) {
        return ApiResponse.error(e).createResponseEntity();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> globalHttpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e,
            WebRequest request
    ) {
        FuriaException exception = new FuriaException(FuriaErrorCode.BAD_REQUEST);
        return ApiResponse.error(exception).createResponseEntity();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> globalNoResourceFoundExceptionHandler(
            NoResourceFoundException e,
            WebRequest request
    ) {
        FuriaException exception = new FuriaException(FuriaErrorCode.ENDPOINT_NOT_FOUND);
        return ApiResponse.error(exception).createResponseEntity();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> globalHttpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e,
            WebRequest request
    ) {
        FuriaException exception = new FuriaException(FuriaErrorCode.METHOD_NOT_ALLOWED);
        return ApiResponse.error(exception).createResponseEntity();
    }

    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<ApiResponse<Object>> globalBeanCreationExceptionHandler(
            BeanCreationException e,
            WebRequest request
    ) {
        if (e.getRootCause() instanceof FuriaException furiaException) {
            return ApiResponse.error(furiaException).createResponseEntity();
        }
        logger.error("Error:", e);
        FuriaException exception = new FuriaException(FuriaErrorCode.INTERNAL);
        return ApiResponse.error(exception).createResponseEntity();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> globalMethodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException e,
            WebRequest request
    ) {
        return ApiResponse.error(new FuriaException(FuriaErrorCode.BAD_REQUEST)).createResponseEntity();
    }
}
