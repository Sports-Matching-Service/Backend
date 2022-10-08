package sportsmatchingservice.exceptions.exception;

import lombok.Getter;
import sportsmatchingservice.constant.ErrorCode;

@Getter
public class GeneralException extends RuntimeException{

    private ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(e), e);
        this.errorCode = errorCode;
    }

}
