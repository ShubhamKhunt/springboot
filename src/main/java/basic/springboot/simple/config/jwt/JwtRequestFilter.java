package basic.springboot.simple.config.jwt;

import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.AuthUserDetails;
import basic.springboot.simple.service.impl.JwtUserDetailServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailServiceImpl jwtUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private Environment env;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 try{
			final String authorizarionHeader = request.getHeader("Authorization");
			
			if(!(
					request.getRequestURI().contains("/auth/") ||
					request.getRequestURI().contains("/tenant/register") ||
					request.getRequestURI().contains("/auth/refreshToken") ||
					request.getRequestURI().contains("/user/acceptInvite")
			) && authorizarionHeader == null){
				SecurityContextHolder.getContext().setAuthentication(null);
	            response.setStatus(401);
	            response.setContentType("application/json");

				new ObjectMapper().writeValue(response.getWriter(), JwtRequestFilter.getObjectMapperInstance("Authorization required"));
	            return;
			}
			
			String username = null;
			String jwt = null;
			
			if (authorizarionHeader != null && authorizarionHeader.startsWith("Bearer")){
				jwt = authorizarionHeader.substring(7);
				username = jwtUtil.getUsernameFromToken(jwt);
			}
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
				AuthUserDetails userDetail = this.jwtUserDetailService.loadUserByUsername(username);
				request.setAttribute("tenantId", userDetail.getTenantId());
				request.setAttribute("userId", userDetail.getUserId());

				System.out.println("userDetail : " + userDetail);
				
				if (jwtUtil.validateToken(jwt, userDetail)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
					
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				} else {
					SecurityContextHolder.getContext().setAuthentication(null);
		            response.setStatus(401);

					new ObjectMapper().writeValue(response.getWriter(), JwtRequestFilter.getObjectMapperInstance("Authorization required"));
		            return;
				}
			}
			
			filterChain.doFilter(request, response);
		} catch(ExpiredJwtException e){
			SecurityContextHolder.getContext().setAuthentication(null);
            response.setStatus(401);
            response.setContentType("application/json");

			new ObjectMapper().writeValue(response.getWriter(), JwtRequestFilter.getObjectMapperInstance("Token expired"));
            
            return;
		} catch(Exception e){
			SecurityContextHolder.getContext().setAuthentication(null);
            response.setStatus(401);
            response.setContentType("application/json");

			new ObjectMapper().writeValue(response.getWriter(), JwtRequestFilter.getObjectMapperInstance(e.getMessage()));
            
            return;
		}
	}

	public static Object getObjectMapperInstance(String msg) throws JsonProcessingException {
		/* ObjectMapper mapper = new ObjectMapper();

		// create a JSON object
		ObjectNode res = mapper.createObjectNode();
		res.put("status", false);
		res.put("message", msg);

		mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res); */

		Map<String, String> errors = new HashMap<>();
		errors.put("err", msg);

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(String.valueOf(HttpStatus.OK));
		apiResponse.setMessage(msg);
		apiResponse.setData(null);
		apiResponse.setError(errors);

		return apiResponse;
	}

}
