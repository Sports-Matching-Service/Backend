package sportsmatchingservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(200, HttpStatus.OK, "Ok"),
    CREATED(201, HttpStatus.CREATED, "Created"),

    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad Request"),
    NOT_FOUND(403, HttpStatus.NOT_FOUND,"Not Found"),

    INTERNAL_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR,"Internal Server Error"),

    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized"),

    FORBIDDEN(403, HttpStatus.FORBIDDEN, "Forbidden")
    ;

    private final Integer code;
    private final HttpStatus status;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + ":" + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }


}
