package se.group3.backend.exceptions;

public class NoUUIDException extends Exception {
    public NoUUIDException(String message) {
        super(message);
    }

    public NoUUIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUUIDException(Throwable cause) {
        super(cause);
    }
}
