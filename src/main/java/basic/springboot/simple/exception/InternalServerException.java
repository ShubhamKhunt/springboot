package basic.springboot.simple.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(){}

    public InternalServerException(String msg){
        super(msg);
    }
}
