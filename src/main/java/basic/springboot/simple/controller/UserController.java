package basic.springboot.simple.controller;

import basic.springboot.simple.exception.AlreadyExistsException;
import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.AcceptInviteDTO;
import basic.springboot.simple.model.DTO.AddUserDTO;
import basic.springboot.simple.service.BaseService;
import basic.springboot.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private UserService userService;

    @PostMapping(path="/")
    public ResponseEntity<Object> add(@Valid @RequestBody AddUserDTO addUserDTO){

        // Validate if exists or not
        if(userService.findByEmail(addUserDTO.getEmail()).isPresent()){
            throw new AlreadyExistsException("User Already Exists");
        }

        return ApiResponse.send(userService.save(addUserDTO));
    }

    /**
     * API : Delete User
     * Date : 03-12-2022
     * @author : Shubham Khunt
     * */
    @DeleteMapping(path="/{userId}")
    public ResponseEntity<Object> remove(@PathVariable Integer userId){
        return ApiResponse.send(userService.remove(userId));
    }

    /**
     * API : Invite User
     * Date : 03-12-2022
     * @author : Shubham Khunt
     * */
    @PostMapping(path="/acceptInvite")
    public ResponseEntity<Object> acceptInvite(@RequestBody AcceptInviteDTO acceptInviteDTO){
        return ApiResponse.send(userService.acceptInvite(acceptInviteDTO));
    }

    /**
     * API : Get User Listing
     * Date : 06-12-2022
     * @author : Shubham Khunt
     * */
    @GetMapping(path="/")
    public ResponseEntity<Object> getUsers(
            @RequestParam(required = true, defaultValue = "0") int pageNo,
            @RequestParam(required = true, defaultValue = "id") String sortBy,
            @RequestParam(required = true, defaultValue = "ASC")  String sortDir
    ){
        return ApiResponse.send(userService.getUsers(pageNo, sortBy, sortDir));
    }
}
