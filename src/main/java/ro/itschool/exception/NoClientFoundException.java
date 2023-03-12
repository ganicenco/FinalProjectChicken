package ro.itschool.exception;

public class NoClientFoundException extends Exception {
    private String message;

    public NoClientFoundException(String message) {
        super(message);
    }
}
