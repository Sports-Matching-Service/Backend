package sportsmatchingservice.constant.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.GeneralException;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public static ApiErrorResponse of(Boolean success, Integer code, String message) {
        return new ApiErrorResponse(success, code, message);
    }

    public static ApiErrorResponse of(ErrorCode errorCode) {
        return new ApiErrorResponse(false, errorCode.getCode(), errorCode.getMessage());
    }

    public static ApiErrorResponse of(GeneralException e) {
        return new ApiErrorResponse(false, e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
}
