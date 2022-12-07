package basic.springboot.simple.service;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.model.AuthUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserDetailService extends UserDetailsService {
    public AuthUserDetails setAuthUserDetails(User user);
}
