package com.indizen.cursoSpring.servicio.service.mail;

import java.io.File;
import java.util.List;

public interface MailService {
	
	public void send(String to, String subject, String text) throws Exception;

	public void send(String to, String subject, String text,List<File> attachments) throws Exception;
}
