package se2.group3.backend.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
