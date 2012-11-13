package com.indizen.cursoSpring.web.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermission;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample.Criteria;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroup;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.grouppermission.GroupPermissionDAO;
import com.indizen.cursoSpring.servicio.persistence.user.UserDAO;
import com.indizen.cursoSpring.servicio.persistence.usergroup.UserGroupDAO;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.web.gui.BeanItem;

@Service("security")
public class SecurityImpl implements Security{
	@Autowired
	private GroupPermissionDAO groupPermissionDAO;
	@Autowired
	private UserGroupDAO userGroupDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SecurityService securityService;

	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyGroupPermission(String idGroup,
			List<BeanItem> listPermission) {
		for(int i=0; i < listPermission.size(); i++){
			BeanItem item = listPermission.get(i);
			GroupPermission groupPermission = new GroupPermission();
			groupPermission.setIdGroup(new java.lang.Integer(idGroup));
			groupPermission.setIdPermission(new  java.lang.Integer(item.getId()));
			groupPermissionDAO.insert(groupPermission);
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyGroupPermissionEdit(String idGroup,
			List<BeanItem> listPermission) {
		GroupPermissionExample example = new GroupPermissionExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdGroupEqualTo(new  java.lang.Integer(idGroup));
		groupPermissionDAO.deleteByExample(example);		
		addManyGroupPermission(idGroup, listPermission);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyGroupUser(String idUser, List<BeanItem> listGroup) {
		for(int i=0; i < listGroup.size(); i++){
			BeanItem item = listGroup.get(i);
			UserGroup userGroup = new UserGroup();
			userGroup.setIdGroup(new  java.lang.Integer(item.getId()));
			userGroup.setIdUser(new  java.lang.Integer(idUser));
			userGroupDAO.insert(userGroup);
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyGroupUserEdit(String idUser, List<BeanItem> listGroup) {
		UserGroupExample example = new UserGroupExample();
		com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample.Criteria criteria = example.createCriteria();
		criteria.andIdUserEqualTo(new  java.lang.Integer(idUser));
		userGroupDAO.deleteByExample(example);		
		addManyGroupUser(idUser, listGroup);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyUserGroupEdit(String idGroup, List<BeanItem> listUser) {
		UserGroupExample example = new UserGroupExample();
		com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample.Criteria criteria = example.createCriteria();
		criteria.andIdGroupEqualTo(new  java.lang.Integer(idGroup));
		userGroupDAO.deleteByExample(example);		
		addManyUserGroup(idGroup, listUser);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addManyUserGroup(String idGroup, List<BeanItem> listUser) {
		for(int i=0; i < listUser.size(); i++){
			BeanItem item = listUser.get(i);
			UserGroup userGroup = new UserGroup();
			userGroup.setIdGroup(new  java.lang.Integer(idGroup));
			userGroup.setIdUser(new  java.lang.Integer(item.getId()));
			userGroupDAO.insert(userGroup);
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public int changePassword(String oldPassword,String newPassword){
		String oldEncode = encodePassword(oldPassword);
		if (!oldEncode.equals(securityService.getUserlogged().getPassword())) {
			return -1;
		}
		if(!securityService.checkPasswordLenght(newPassword))
		{
			return -2;
		}
		if(!securityService.checkPasswordCharacter(newPassword))
		{
			return -3;
		}
		String newEncode = encodePassword(newPassword);
		UserLogged userLogged = securityService.getUserlogged();
		userLogged.getUser().setPassword(newEncode);
		userDAO.updateByPrimaryKey(userLogged.getUser());
		return 0;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public int changePasswordRequired(String oldPassword, String newPassword) {
		String oldEncode = encodePassword(oldPassword);
		if (!oldEncode.equals(securityService.getUserlogged().getPassword())) {
			return -1;
		}
		if(!securityService.checkPasswordLenght(newPassword))
		{
			return -2;
		}
		if(!securityService.checkPasswordCharacter(newPassword))
		{
			return -3;
		}
		String newEncode = encodePassword(newPassword);
		UserLogged userLogged = securityService.getUserlogged();
		userLogged.getUser().setPassword(newEncode);
		userLogged.setValidatePassword(true);
		userLogged.getUser().setDateLastPassword(new Timestamp(new Date().getTime()));
		userDAO.updateByPrimaryKey(userLogged.getUser());
		return 0;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllGroupPermission(String idGroup) {
		GroupPermissionExample example = new GroupPermissionExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdGroupEqualTo(new  java.lang.Integer(idGroup));
		groupPermissionDAO.deleteByExample(example);	
	}
	
	public String encodePassword(String password) {
		return passwordEncoder.encodePassword(password, null);
	}

}
