package com.indizen.cursoSpring.servicio.service.mail;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("mailService")
public class MailServiceImpl implements MailService {
	private static final Logger LOGGER = Logger.getLogger("serviceManager");
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private SimpleMailMessage templateMessage;

	public void send(String to, String subject, String text) throws MessagingException {
		send(to, subject, text, null);
	}

	public void send(String to, String subject, String text,
			List<File> attachments) throws MessagingException {

		// asegurando la trazabilidad
		if (LOGGER.isDebugEnabled()) {
			final boolean usingPassword = !"".equals(mailSender.getPassword());
			LOGGER.debug("Sending email to: '" + to + "' [through host: '"
					+ mailSender.getHost() + ":" + mailSender.getPort()
					+ "', username: '" + mailSender.getUsername()
					+ "' usingPassword:" + usingPassword + "].");

		}

		// plantilla para el envio de email
		final MimeMessage message = mailSender.createMimeMessage();

		// el flag a true indica que va a ser multipart
		final MimeMessageHelper helper = new MimeMessageHelper(message, true);

		// settings de los parametros del envio
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		helper.setFrom(templateMessage.getFrom());

		// adjuntando los ficheros
		if (attachments != null && attachments.size() > 0) {
			for (int i = 0; i < attachments.size(); i++) {
				FileSystemResource file = new FileSystemResource(attachments
						.get(i));
				helper.addAttachment(attachments.get(i).getName(), file);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("File '" + file + "' attached.");
				}
			}
		}

		// el envio
		this.mailSender.send(message);
	}
}