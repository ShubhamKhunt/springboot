package basic.springboot.simple.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(){}

    public AlreadyExistsException(String msg){
        super(msg);
    }
}
