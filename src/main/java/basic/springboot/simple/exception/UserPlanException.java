package basic.springboot.simple.exception;

public class UserPlanException extends RuntimeException {
    public UserPlanException(){
        super();
    }

    public UserPlanException(String msg){
        super(msg);
    }
}
