package org.icervantes.uber.email.provider.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.sendgrid.SendGrid;

/**
 * Provider of <code>SendGrid</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class SendGridClientProvider implements Provider<SendGrid> {
	private static final Logger log = Logger.getLogger( SendGridClientProvider.class.getName() );

	@Inject
	SendGridAuth auth;
	
	/**
	 * Provides an instance of SendGrid
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	@Override
	public SendGrid get() {
		try{
			SendGrid sendgrid = new SendGrid(auth.getUser(), auth.getPass());
			return sendgrid;
		} catch (Exception e) {
			log.log(Level.SEVERE, 
					String.format("Error building SendGrid client: %s",
							e.getMessage()));
			return null;
		}	
	}
}
