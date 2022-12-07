package basic.springboot.simple.exception;

import basic.springboot.simple.model.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fName, message.substring(0, 1).toUpperCase() + message.substring(1));
        });

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.BAD_REQUEST, errors), HttpStatus.CREATED);
    }

    @ExceptionHandler(value={AlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.OK, errors), HttpStatus.OK);
    }

    @ExceptionHandler(value={InternalServerException.class})
    public ResponseEntity<Object> handleInternalServerException(InternalServerException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.INTERNAL_SERVER_ERROR, errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Bad / Wrong Credentials
    @ExceptionHandler(value={NoDataFoundException.class})
    public ResponseEntity<Object> handleNoDataFound(Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.OK, errors), HttpStatus.OK);
    }

    // Bad / Wrong Credentials
    @ExceptionHandler(value={BadCredential.class})
    public ResponseEntity<Object> handleBadCredentials(Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.OK, errors), HttpStatus.OK);
    }

    @ExceptionHandler(value={UserPlanException.class})
    public ResponseEntity<Object> handleUserPlanException(Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(HttpStatus.OK, errors), HttpStatus.OK);
    }

    // This is Global Exception, hence need to be at very last in this file
    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("err", ex.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex.getClass().getSimpleName().equals("DataIntegrityViolationException")){
            status = HttpStatus.OK;
            errors.put("err", "Email address already exists");
        }

        return new ResponseEntity<Object>(GenericExceptionHandler.getApiResponseInstance(status, errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ApiResponse getApiResponseInstance(HttpStatus status, Map<String, String> errors){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(String.valueOf(status));
        apiResponse.setMessage("please find in error object for errors");
        apiResponse.setData(null);
        apiResponse.setError(errors);

        return apiResponse;
    }
}

