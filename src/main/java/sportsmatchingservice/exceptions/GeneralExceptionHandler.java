package sportsmatchingservice.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiErrorResponse;
import sportsmatchingservice.exceptions.exception.DuplicatedDataException;
import sportsmatchingservice.exceptions.exception.GeneralException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleException(GeneralException e) {
        return ApiErrorResponse.of(e);
    }

    @ExceptionHandler(DuplicatedDataException.class)
    public ApiErrorResponse handleException(DuplicatedDataException e) {
        return ApiErrorResponse.of(e);
    }
}
