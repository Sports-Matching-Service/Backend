package sportsmatchingservice.constant.dto;


import lombok.Getter;
import lombok.ToString;
import sportsmatchingservice.constant.ErrorCode;

@Getter
@ToString
public class ApiDataResponse<T> extends ApiErrorResponse {

    private final T data;

    private ApiDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    private ApiDataResponse(ErrorCode errorCode, T data){
        super(true, errorCode.getCode(), errorCode.getMessage());
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of(T data) {
        return new ApiDataResponse<>(data);
    }

    public static <T> ApiDataResponse<T> empty() {
        return new ApiDataResponse<>(null);
    }

    public static <T> ApiDataResponse<T> of(ErrorCode errorCode, T data){
        return new ApiDataResponse<>(errorCode, data);
    }
}
