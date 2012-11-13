package com.indizen.cursoSpring.web.security;

import java.util.List;

import com.indizen.cursoSpring.web.gui.BeanItem;


public interface Security {
	
	public void addManyGroupPermission(String idGroup,List<BeanItem> listPermission);

	public void addManyGroupPermissionEdit(String idGroup,List<BeanItem> listPermission);

	public void deleteAllGroupPermission(String idGroup);
	
	public void addManyUserGroupEdit(String idGroup, List<BeanItem> listUser);
	
	public void addManyGroupUserEdit(String idUser, List<BeanItem> listGroup);
	
	public void addManyUserGroup(String idGroup, List<BeanItem> listUser);
	
	public void addManyGroupUser(String idUser, List<BeanItem> listGroup);
	
	public String encodePassword(String password);
	
	public int changePassword(String oldPassword, String newPassword);
	
	public int changePasswordRequired(String oldPassword, String newPassword);

}
