package com.indizen.cursoSpring.servicio.service.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;

public interface SecurityService {

	public List<VGroupPermission> getGroupPermissionExample(
			VGroupPermissionExample example);
	
	public List<User> getSelectExample(UserExample userExample);
	
	public UserLogged getUserlogged();
	
	public UserDetails obtenerUsuario();
	
	public void deleteUser(User aUser);
	
	public void updateUser(User aUser);

	public void attemptsIncreaseLogin(String usuario);
	
	public void addUser(User aUser);

	public void deleteAllGroup(String idGroup);
	
	public List<VUserGroup> getUserGroupExample(VUserGroupExample example);
	
	public void deleteGroup(Group aGroup);
	
	public void deletePermission(Permission aPermission);
	
	public List<Permission> getPermissions();
	
	public List<User> getUsers();
	
	public List<Group> getGroups();
	
	public String generatePassword();
	
	public boolean checkPasswordCharacter(String password);
	
	public boolean checkPasswordLenght(String password);
	
	public int getMinLengthPassword();
	
	public boolean checkTelephone(String telephone);
	
	public boolean checkEmail(String email);
	
	public boolean checkValidatePasswordUser(User user);
	
	public boolean checKCurrentBlockedUser(User user);
}