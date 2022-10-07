package sportsmatchingservice.exceptions.exception;

import lombok.Getter;
import sportsmatchingservice.constant.ErrorCode;

@Getter
public class DuplicatedDataException extends GeneralException{

    public DuplicatedDataException(ErrorCode errorCode,Throwable e) {
        super(errorCode, e);
    }

}
