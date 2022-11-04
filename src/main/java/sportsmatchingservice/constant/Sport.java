package sportsmatchingservice.constant;

import lombok.Getter;

public enum Sport {
    BASKETBALL("농구"),
    SOCCER("축구"),
    TENNIS("테니스"),
    BOWLING("볼링"),
    FUTSAL("풋살");

    @Getter
    private final String message;

    Sport(String message) {
        this.message = message;
    }
}
