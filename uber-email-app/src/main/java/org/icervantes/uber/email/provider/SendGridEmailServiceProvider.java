package org.icervantes.uber.email.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.client.SendGridClientProvider;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

/**
 * SenGrid email service provider implementation.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class SendGridEmailServiceProvider<T extends SimpleEmail> implements
		EmailServiceProvider<T> {
	private static final Logger log = Logger.getLogger( SendGridEmailServiceProvider.class.getName() );

	@Inject
	SendGridClientProvider client;

	/**
	 * @see org.icervantes.uber.email.provider.EmailServiceProvider#send(org.icervantes.uber.email.model.SimpleEmail)
	 */
	@Override
	public String send(T t) {
		try {
			SendGrid sendGrid = client.get();
			SendGrid.Response response = sendGrid.send(buildEmail(t));
			return response.getMessage();
		} catch (SendGridException e) {
			log.log(Level.SEVERE, 
					String.format("Error calling SendGrid: %s",
							e.getMessage()));
			return "ERROR";
		} catch (Exception e) {
			log.log(Level.SEVERE, 
					String.format("Error calling SendGrid: %s",
							e.getMessage()));
			return "ERROR";
		}
	}
	
	private SendGrid.Email buildEmail(T t) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(t.getTo());
		email.setFrom(t.getFrom());
		email.setSubject(t.getSubject());
		email.setText(t.getBody());
		return email;
	}
}
