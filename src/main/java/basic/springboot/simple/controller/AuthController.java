package basic.springboot.simple.controller;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.BadCredential;
import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.Auth.AuthenticationResponse;
import basic.springboot.simple.model.DTO.Auth.RefreshTokenDTO;
import basic.springboot.simple.model.DTO.UserLoginDTO;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(path="/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/")
    public ResponseEntity<Object> authLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        } catch(BadCredentialsException e){
            throw new BadCredential("Wrong credential");
        }

        // Get User
        User user = userRepository.findByEmail(userLoginDTO.getUsername());

        if(Objects.isNull(user)){
            throw new BadCredentialsException("Wrong Credentials");
        }

        AuthenticationResponse authenticationResponse = authService.authLogin(user);

        return ApiResponse.sendWithAuth(user, authenticationResponse);
    }

    @PostMapping(path="/refreshToken")
    public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDto){

        // Get User
        User user = userRepository.findByRefreshToken(refreshTokenDto.getRefreshToken());

        if(Objects.isNull(user)){
            throw new BadCredentialsException("Wrong Refresh Token");
        }

        AuthenticationResponse authenticationResponse = authService.authLogin(user);

        return ApiResponse.send(authenticationResponse);
    };
}
