package ro.itschool.exception;

public class NoProductException extends Exception{
    private String message;

    public NoProductException (String message){
        super(message);
    }
}
