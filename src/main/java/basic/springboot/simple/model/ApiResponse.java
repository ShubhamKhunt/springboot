package basic.springboot.simple.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
    private Object Data;
    private Object error;
    private Object auth;

    public static ResponseEntity<Object> send(Object data){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully called!");
        apiResponse.setStatus(String.valueOf(HttpStatus.OK));
        apiResponse.setData(data);
        apiResponse.setError(null);

        return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
    }

    public static ResponseEntity<Object> sendWithAuth(Object data, Object auth){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully called!");
        apiResponse.setStatus(String.valueOf(HttpStatus.OK));
        apiResponse.setData(data);
        apiResponse.setAuth(auth);
        apiResponse.setError(null);

        return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
    }
}
