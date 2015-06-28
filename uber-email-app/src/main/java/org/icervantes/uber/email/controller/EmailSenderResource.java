package org.icervantes.uber.email.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.icervantes.uber.email.inject.EmailServiceModule;
import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.service.ApplicationService;
import org.icervantes.uber.email.service.EmailApplicationService;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * A resource to send an email. API exposed thru a RESTful Web Service with root
 * path as <b>services</b>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 */
@Path("services")
public class EmailSenderResource {
	private static final Logger log = Logger
			.getLogger(EmailSenderResource.class.getName());
	private static final Injector injector = Guice
			.createInjector(new EmailServiceModule());

	private final ApplicationService service = injector
			.getInstance(EmailApplicationService.class);

	/**
	 * Send an email which is consumed as a JSON object an mapped to
	 * <code>SimpleEmail</code>
	 * 
	 * @param simpleEmail
	 *            that contains the basic data of an email
	 * @return a response message that could be a email id or an error message.
	 */
	@POST
	@Path("/sendEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	public String sendEmail(SimpleEmail simpleEmail) {
		log.log(Level.INFO, String.format("Inside POST request: %s",
				simpleEmail.toString()));
		return service.process(simpleEmail);
	}
}
