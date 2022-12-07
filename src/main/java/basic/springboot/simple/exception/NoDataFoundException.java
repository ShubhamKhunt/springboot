package basic.springboot.simple.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException() {
        super();
    }

    public NoDataFoundException(String msg) {
        super(msg);
    }
}
