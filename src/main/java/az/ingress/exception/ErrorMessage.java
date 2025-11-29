package az.ingress.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    UNEXPECTED_ERROR("Unexpected error occurred"),
    CLIENT_ERROR("Error from client");

    private final String value;
}