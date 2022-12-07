package basic.springboot.simple.exception;

public class BadCredential extends RuntimeException {
    public BadCredential(){
        super();
    }

    public BadCredential(String msg){
        super(msg);
    }
}
