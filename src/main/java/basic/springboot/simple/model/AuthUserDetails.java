package basic.springboot.simple.model;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoRepositoryBean
public class AuthUserDetails implements UserDetails  {
	
	private Collection<? extends GrantedAuthority> authorities;

	private Integer tenantId;
	
	private Integer userId;

	private String displayName;

	private String password;

	private String username;

	private Boolean enabled;

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private boolean credentialsNonExpired;

    private String refreshToken;

	public AuthUserDetails(Integer tenantId, Integer userId, String displayName, String username, String password, String refreshToken, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
	    this.tenantId = tenantId;
	    this.userId = userId;
	    this.displayName = displayName;
	    this.enabled=enabled;
	    this.username=username;
	    this.password=password;
        this.refreshToken = refreshToken;
	    this.accountNonExpired=true;
	    this.accountNonLocked=true;
	    this.credentialsNonExpired=true;
	    this.authorities=authorities;
	}

	public AuthUserDetails(Integer tenantId, Integer userId, String displayName, String password, String username, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.tenantId = tenantId;
        this.userId = userId;
        this.displayName = displayName;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void eraseCredentials(){
        this.password=null;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AuthUserDetails{" +
                "authorities=" + authorities +
                ", tenantId=" + tenantId +
                ", userId=" + userId +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
