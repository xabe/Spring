package com.indizen.cursoSpring.servicio.service.security;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample.Criteria;
import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample;
import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.group.GroupDAO;
import com.indizen.cursoSpring.servicio.persistence.grouppermission.GroupPermissionDAO;
import com.indizen.cursoSpring.servicio.persistence.permission.PermissionDAO;
import com.indizen.cursoSpring.servicio.persistence.user.UserDAO;
import com.indizen.cursoSpring.servicio.persistence.usergroup.UserGroupDAO;
import com.indizen.cursoSpring.servicio.persistence.vgrouppermission.VGroupPermissionDAO;
import com.indizen.cursoSpring.servicio.persistence.vusergroup.VUserGroupDAO;
import com.indizen.cursoSpring.servicio.util.Constants;
import com.indizen.cursoSpring.servicio.util.DateUtils;

@SuppressWarnings("unchecked")
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private GroupPermissionDAO groupPermissionDAO;
	@Autowired
	private UserGroupDAO userGroupDAO;
	@Autowired
	private VGroupPermissionDAO vgroupPermissionDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private VUserGroupDAO vuserGroupDAO;
	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private PermissionDAO permissionDAO;

	@Value("3")
	private int maxAttemptsLogin;
	@Value("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.-+_")
	private String passwordCharacter;
	@Value("4")
	private int passwordLength;
	@Value("^([a-z|A-Z|0-9|\\+|\\.|\\-|_|,])+$")
	private String passwordPattern;
	@Value("^(\\+?\\d{9,12})$")
	private String telephonePattern;
	private static final String EMAIL_PATTERN = "(\\w([_.\\-]*))+@(\\w([_.\\-]*))+\\.\\w{2,6}";
	@Value("10")
	private int timeBlockedLogin;
	@Value("6")
	private int timeValidatePasswordLogin;

	public void setMaxAttemptsLogin(int maxAttemptsLogin) {
		this.maxAttemptsLogin = maxAttemptsLogin;
	}

	public void setPasswordCharacter(String passwordCharacter) {
		this.passwordCharacter = passwordCharacter;
	}

	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}

	public void setPasswordPattern(String passwordPattern) {
		this.passwordPattern = passwordPattern;
	}

	public void setTelephonePattern(String telephonePattern) {
		this.telephonePattern = telephonePattern;
	}

	public void setTimeBlockedLogin(int timeBlockedLogin) {
		this.timeBlockedLogin = timeBlockedLogin;
	}

	public void setTimeValidatePasswordLogin(int timeValidatePasswordLogin) {
		this.timeValidatePasswordLogin = timeValidatePasswordLogin;
	}

	@Transactional(readOnly=true)
	public List<VGroupPermission> getGroupPermissionExample(
			VGroupPermissionExample example) {
		return vgroupPermissionDAO.selectByExample(example);
	}

	public UserLogged getUserlogged() {
		Object obj = SecurityContextHolder.getContext().getAuthentication() == null ? null
				: SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		if (obj instanceof UserLogged)
			return (UserLogged) obj;
		return null;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteUser(User aUser) {
		UserGroupExample userGroupExample = new UserGroupExample();
		userGroupExample.createCriteria().andIdUserEqualTo(aUser.getId());
		userGroupDAO.deleteByExample(userGroupExample);
		userDAO.deleteByPrimaryKey(aUser.getId());
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateUser(User aUser) {
		userDAO.updateByPrimaryKey(aUser);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void attemptsIncreaseLogin(String usuario) {
		int loginAttempts = 0;
		UserExample userExample = new UserExample();

		com.indizen.cursoSpring.servicio.model.user.UserExample.Criteria criteria = userExample
				.createCriteria();
		criteria.andUsernameEqualTo(usuario);
		List<User> lista = userDAO.selectByExample(userExample);

		if (lista.size() > 0) {
			User user = lista.get(0);
			if (Constants.getBoolean(user.getEnable())
					&& !Constants.getBoolean(user.getBlocked())) {
				// Ponemos los intentos de login a 0 cuando pasa un dia desde
				// ultimo intento de login
				if (user.getDateLastLogin() != null
						&& user.getDateLastLogin().getTime() != 0) {
					Calendar currentDay = Calendar.getInstance();
					currentDay.setTime(new Date());
					currentDay.set(Calendar.HOUR_OF_DAY, 0);
					currentDay.set(Calendar.MINUTE, 0);
					currentDay.set(Calendar.SECOND, 0);
					currentDay.set(Calendar.MILLISECOND, 0);

					Calendar lastDayLogin = Calendar.getInstance();
					lastDayLogin.setTime(user.getDateLastLogin());
					lastDayLogin.set(Calendar.HOUR_OF_DAY, 0);
					lastDayLogin.set(Calendar.MINUTE, 0);
					lastDayLogin.set(Calendar.SECOND, 0);
					lastDayLogin.set(Calendar.MILLISECOND, 0);
					long valor = currentDay.getTime().getTime()
							- lastDayLogin.getTime().getTime();
					if (valor >= 86400000)
						user.setAttemptsLogin(Constants.getCero(user
								.getAttemptsLogin()));
				}

				if (user.getAttemptsLogin() != null) {
					loginAttempts = user.getAttemptsLogin().intValue();
				}

				loginAttempts++;

				if (loginAttempts < maxAttemptsLogin) {
					user.setAttemptsLogin(Constants.getValor(
							user.getAttemptsLogin(), loginAttempts));
					userDAO.updateByPrimaryKey(user);
				} else {
					user.setBlocked(Constants.getTrue(user.getId()));
					user.setAttemptsLogin(Constants.getValor(
							user.getAttemptsLogin(), loginAttempts));
					userDAO.updateByPrimaryKey(user);
				}
			} else if (Constants.getBoolean(user.getEnable())
					&& Constants.getBoolean(user.getBlocked())) {
				if (checKCurrentBlockedUser(user)) {
					user.setBlocked(Constants.getFalse(user.getBlocked()));
					user.setAttemptsLogin(Constants.getCero(user
							.getAttemptsLogin()));
					userDAO.updateByPrimaryKey(user);
				}
			}
		}
	}
	
	@Transactional(readOnly=true)
	public List<User> getSelectExample(UserExample userExample) {
		return userDAO.selectByExample(userExample);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void addUser(User aUser) {
		userDAO.insert(aUser);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllGroup(String idGroup) {
		UserGroupExample example = new UserGroupExample();
		com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample.Criteria criteria = example
				.createCriteria();
		criteria.andIdGroupEqualTo(new java.lang.Integer(idGroup));
		userGroupDAO.deleteByExample(example);
	}

	@Transactional(readOnly=true)
	public List<VUserGroup> getUserGroupExample(VUserGroupExample example) {
		return vuserGroupDAO.selectByExample(example);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteGroup(Group aGroup) {
		UserGroupExample example = new UserGroupExample();
		com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample.Criteria criteria = example
				.createCriteria();
		criteria.andIdGroupEqualTo(aGroup.getId());
		userGroupDAO.deleteByExample(example);

		GroupPermissionExample example2 = new GroupPermissionExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andIdGroupEqualTo(aGroup.getId());
		groupPermissionDAO.deleteByExample(example2);

		groupDAO.deleteByPrimaryKey(aGroup.getId());
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deletePermission(Permission aPermission) {
		GroupPermissionExample example2 = new GroupPermissionExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andIdPermissionEqualTo(aPermission.getId());
		groupPermissionDAO.deleteByExample(example2);

		permissionDAO.deleteByPrimaryKey(aPermission.getId());
	}

	@Transactional(readOnly=true)
	public List<Permission> getPermissions() {
		return permissionDAO.selectByExample(new PermissionExample());
	}

	@Transactional(readOnly=true)
	public List<Group> getGroups() {
		return groupDAO.selectByExample(new GroupExample());
	}

	@Transactional(readOnly=true)
	public List<User> getUsers() {
		return userDAO.selectByExample(new UserExample());
	}

	public String generatePassword() {
		String pswd = "";
		for (int i = 0; i < passwordLength; i++) {
			pswd += (passwordCharacter
					.charAt((int) (Math.random() * passwordCharacter.length())));
		}
		return pswd;
	}

	public boolean checkPasswordCharacter(String password) {
		return Pattern.compile(passwordPattern).matcher(password).find();
	}

	public boolean checkTelephone(String telephone) {
		return Pattern.compile(telephonePattern).matcher(telephone).find();
	}

	public boolean checkEmail(String email) {
		return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
	}

	public boolean checkPasswordLenght(String password) {
		return password.trim().length() >= passwordLength;
	}

	public boolean checkValidatePasswordUser(User user) {
		if (user.getDateLastPassword() == null)
			return false;
		else {
			Timestamp currentTime = new Timestamp(new Date().getTime());
			Timestamp lastPassword = DateUtils.diaSumandoMeses(
					timeValidatePasswordLogin, user.getDateLastPassword());
			return currentTime.after(lastPassword);
		}
	}

	public boolean checKCurrentBlockedUser(User user) {
		if (Constants.getBoolean(user.getBlocked())) {
			Timestamp currentTime = new Timestamp(new Date().getTime());
			Timestamp lastPassword = DateUtils.diaSumandoMinutos(
					timeBlockedLogin, user.getDateLastLogin());
			return currentTime.after(lastPassword);
		}
		return true;
	}

	public int getMinLengthPassword() {
		return passwordLength;
	}

	public UserDetails obtenerUsuario() {
		UserDetails userDetails = new UserLogged(new User());
		((UserLogged) userDetails).getUser().setUsername(Constants.ANONYMOUSLY);
	
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
				Object object = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
			if (object instanceof UserDetails)
				userDetails = (UserDetails) object;
		}
		return userDetails;
	}
}