package basic.springboot.simple.service.impl;

import basic.springboot.simple.config.jwt.JwtUtil;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.BadCredential;
import basic.springboot.simple.model.AuthUserDetails;
import basic.springboot.simple.model.DTO.Auth.AuthenticationRequest;
import basic.springboot.simple.model.DTO.Auth.AuthenticationResponse;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthServiceImpl {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationResponse authLogin(User user) {

        final AuthUserDetails userDetails = jwtUserDetailService.setAuthUserDetails(user);

        // Generate Fresh AccessToken
        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        try{
            // Generate RefreshToken if null
            String refreshToken = jwtTokenUtil.generateRefreshToken(user.getEmail());

            // Update refresh token and last login of user
            user.setLastLogin(new Date());
            user.setRefreshToken(refreshToken);
            user.setModified(new Date());
            userRepository.save(user);

            return new AuthenticationResponse(accessToken, refreshToken);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
