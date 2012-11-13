package com.indizen.cursoSpring.web.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.user.UserExample.Criteria;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.servicio.util.DateUtils;
import com.indizen.cursoSpring.web.util.Constants;


@Service("loginCheck")
public class LoginCheck implements UserDetailsService {
	private static final Logger LOGGER = Logger.getLogger("beanManager");
	@Autowired
	private SecurityService securityService;
	

	public void changePassword(String arg0, String arg1) {

	}

	public void createUser(LoginCheck arg0) {

	}

	public void deleteUser(String arg0) {

	}
	
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException, DataAccessException {
		if (null == login) {
			LOGGER.error("Error login is null");
			throw new IllegalArgumentException(
					"Login is mandatory. Null value is forbidden.");
		}
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsernameLike(login);
		List<User> users = securityService.getSelectExample(userExample);

		if (users == null || users.size() == 0) {
			LOGGER.error("User with login: " + login
					+ " not found in database. Authentication failed for user "
					+ login);
			throw new UsernameNotFoundException("user not found in database");
		} else {

			User userLogin = (User) users.get(0);
			UserLogged userLogged = new UserLogged(userLogin);
			userLogin.setDateLastLogin(new Timestamp(new Date().getTime()));
			//Actualizamos la fecha de last login
			if(!Constants.getBoolean(userLogin.getBlocked()))
			{
				securityService.updateUser(userLogin);
			}
			
			//Comprobamos que la fecha esta vigente
			userLogged.setValidatePassword(true);
			if(securityService.checkValidatePasswordUser(userLogin))
			{
				LOGGER.debug("the user: " +userLogin.getUsername()+" has expired password must necessarily change");
				userLogged.setValidatePassword(false);
			}
			//Obtenemos todos los grupos del usuario
			VUserGroupExample example = new VUserGroupExample();
			com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample.Criteria criteria2 = example.createCriteria();
			criteria2.andIdUserEqualTo(userLogin.getId());
			
			List<VUserGroup> groupViews = securityService.getUserGroupExample(example);
			//Obtenemos todos los permissions
			VGroupPermissionExample groupPermissionExample = new VGroupPermissionExample();
			com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample.Criteria criteria3 = groupPermissionExample.createCriteria();
			List<java.lang.Integer> listGroups = new ArrayList<java.lang.Integer>();
			for(int i=0; i < groupViews.size(); i ++){
				listGroups.add(groupViews.get(i).getIdGroup());
			}
			
			if (listGroups.size() == 0) {
				LOGGER.error("User with login: " + login
						+ " don't have groups " + login);
				throw new AuthenticationServiceException(
						"No tiene grupos asociados");

			}
			criteria3.andIdGroupIn(listGroups);
			
			List<VGroupPermission> permissions = securityService.getGroupPermissionExample(groupPermissionExample);
			
			if (permissions.size() == 0) {
				LOGGER.error("User with login: " + login
						+ " don't have permissions  " + login);
				throw new AuthenticationServiceException(
						"No tiene permisos asociados");

			}

			int numPermisos = permissions.size();
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
			// y si convierto Usuario en un GrantedAuthority??
			for (int i = 0; i < numPermisos; i++) {
				roles
						.add(new GrantedAuthorityImpl(permissions.get(i)
								.getNamePermission()));
			}
			userLogged.setAuthorities(roles);
			LOGGER.debug("User :"+userLogin.getUsername()+" logged : "+DateUtils.obtenerFechaFormato(new Date(),"dd/MM/yyy HH:mm:ss"));
			return userLogged;	
		}
	}

	public void updateUser(LoginCheck arg0) {

	}

	public boolean userExists(String arg0) {
		return false;
	}
}