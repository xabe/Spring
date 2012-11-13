package com.indizen.cursoSpring.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

import com.indizen.cursoSpring.servicio.service.security.SecurityService;

@Component
public class ListenerAutentificacion implements ApplicationListener<ApplicationEvent> {
	@Autowired
	private SecurityService securityService;


	public void onApplicationEvent(final ApplicationEvent e) {
	
		//if (e instanceof AbstractAuthenticationEvent) {
			//if (e instanceof InteractiveAuthenticationSuccessEvent) {
				// handle InteractiveAuthenticationSuccessEvent
			//} else if (e instanceof AbstractAuthenticationFailureEvent) {
				// handle AbstractAuthenticationFailureEvent
			//} else if (e instanceof AuthenticationSuccessEvent) {
				// handle AuthenticationSuccessEvent
			//} else if (e instanceof AuthenticationSwitchUserEvent) {
				// handle AuthenticationSwitchUserEvent
			//} else {
				// handle other authentication event
			//}
		//} else if (e instanceof AbstractAuthorizationEvent) {
			// handle authorization event
		//}
		if (e instanceof AbstractAuthenticationEvent && e instanceof AbstractAuthenticationFailureEvent) {
			AbstractAuthenticationFailureEvent event = (AbstractAuthenticationFailureEvent) e;
			String username = event.getAuthentication().getName();
			if(username != null && username.length() > 0){
				securityService.attemptsIncreaseLogin(username);
			}
		}
		
	}
}