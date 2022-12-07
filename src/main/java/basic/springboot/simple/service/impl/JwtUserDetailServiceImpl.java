package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.model.AuthUserDetails;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class JwtUserDetailServiceImpl implements JwtUserDetailService {

	@Autowired
	private UserRepository userRepositoy;

	@Override
	public AuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepositoy.findByEmail(username);
		
		if(Objects.isNull(user)){
			return null;
		}

		AuthUserDetails authUser = new AuthUserDetails(user.getTenant().getId(), user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRefreshToken(), true, new ArrayList<>());
		return authUser;
	}

	public AuthUserDetails setAuthUserDetails(User user) {

		AuthUserDetails authUser = new AuthUserDetails(user.getTenant().getId(), user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRefreshToken(), true, new ArrayList<>());
		return authUser;
	}
}
