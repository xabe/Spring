package com.indizen.cursoSpring.servicio.util;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.indizen.cursoSpring.servicio.service.security.SecurityService;

@Aspect
@Component
public class LogInterceptor {
	private static final Logger LOGGER = Logger.getLogger("CapaServicio");	
	@Autowired
	private SecurityService securityService;
	
	@Around(value="execution(* com.indizen.cursoSpring.servicio.service.persistence..*.*(..))")
    public Object monitorDAO(ProceedingJoinPoint pjp) throws Throwable {
    	UserDetails user = securityService.obtenerUsuario();
    	String metodo = "The user logged : "+user.getUsername()+" - "+pjp.getTarget().getClass().toString()+" - "+ pjp.getSignature().getDeclaringType() + "."
				+ pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		StringBuffer text = new StringBuffer();
		for(int i=0; i < args.length; i++){
			if(i == 0 || i == args.length-1)
			{
				text.append(args[i]==null?"null":args[i].toString());
			}
			else
			{
				text.append(args[i]==null?"null":args[i].toString());
				text.append(", ");
			}
		}
		long start = System.nanoTime();
		try {
			return pjp.proceed();
		} finally {
			long time = (System.nanoTime() - start);
			long millis = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
			LOGGER.debug(metodo + "(" + text.toString() + ")" + ": " + millis/Constants.TIME + "seconds");
		}
	}
    
    		@Around(value="execution(* com.indizen.cursoSpring.servicio.service..*.*(..)) and !execution(* com.indizen.cursoSpring.servicio.service.security.SecurityService.getUserlogged()) and !execution(* com.indizen.cursoSpring.servicio.service.security.SecurityService.obtenerUsuario())")
	    public Object monitorService(ProceedingJoinPoint pjp) throws Throwable {
    	UserDetails user = securityService.obtenerUsuario();
    	String metodo = "The user logged : "+user.getUsername()+" - "+pjp.getTarget().getClass().toString()+" - "+ pjp.getSignature().getDeclaringType() + "."
				+ pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		StringBuffer text = new StringBuffer();
		for(int i=0; i < args.length; i++){
			if(i == 0 || i == args.length-1)
			{
				text.append(args[i]==null?"null":args[i].toString());
			}
			else
			{
				text.append(args[i]==null?"null":args[i].toString());
				text.append(", ");
			}
		}
		long start = System.nanoTime();
		try {
			return pjp.proceed();
		} finally {
			long time = (System.nanoTime() - start);
			long millis = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
			LOGGER.debug(metodo + "(" + text.toString() + ")" + ": " + millis/Constants.TIME + "seconds");
		}
	}
	
	}
