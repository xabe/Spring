package com.indizen.cursoSpring.servicio.model.userLogged;

import java.io.Serializable;
import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.util.Constants;

public class UserLogged implements UserDetails, Serializable{
	private static final long serialVersionUID = 1L;
	private User user;
    private Collection<GrantedAuthority> roles;
    private boolean validatePassword;

    public UserLogged(User user) {
		this.user = user;
	}
    
    public String getPassword() {
    	return user.getPassword();
    }

    public String getUsername() {
    	return user.getUsername();
    }
    
    public void setUser(User user) {
		this.user = user;
	}
    
    public User getUser() {
		return user;
	}
    
    public boolean isValidatePassword() {
		return validatePassword;
	}

	public void setValidatePassword(boolean validatePassword) {
		this.validatePassword = validatePassword;
	}
    
    public Collection<GrantedAuthority> getAuthorities() {
    	return roles;
    }
    
    public void setAuthorities(Collection<GrantedAuthority> roles) {
		this.roles = roles;
	}
    
    public boolean isAccountNonExpired() {
    	return true;
    }
    
    public boolean isAccountNonLocked() {
    	return !Constants.getBoolean(user.getBlocked());
    }
    
    public boolean isCredentialsNonExpired() {
    	return true;
    }
    
    public boolean isEnabled() {
    	return Constants.getBoolean(user.getEnable());
    }
    
    public boolean equals(Object obj) {
    	if(obj == this)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		final UserLogged other  = (UserLogged) obj;
		if(user == null)
		{
			if(other.getUser() != null)
				return false;
		}
		else if(!user.getId().equals(other.getUser().getId()))
			return false;
		
		if(user == null)
		{
			if(other.getUser().getUsername() != null)
				return false;
		}
		else if(!user.getUsername().equals(other.getUser().getUsername()))
			return false;
		return true;
    }
    

    public int hashCode() {
    	int result = 17;
        result = 37*result + user.getId().hashCode();
        result = 37*result + user.getUsername().hashCode();
        return result;
    }
}