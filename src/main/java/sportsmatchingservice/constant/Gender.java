package sportsmatchingservice.constant;

import lombok.Getter;

public enum Gender {
    FEMALE("여성"),
    MALE("남성"),
    CONCOCTION("혼성");

    @Getter
    private final String message;

    Gender(String message) {
        this.message = message;
    }
}
