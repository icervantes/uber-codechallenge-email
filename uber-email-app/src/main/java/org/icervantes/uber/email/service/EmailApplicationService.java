package org.icervantes.uber.email.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.EmailServiceProvider;

import com.google.common.collect.ImmutableList;

/**
 * Process an email. Delegates to a third party email service.
 * Basic fail-over implementation.
 *  
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class EmailApplicationService implements ApplicationService {
	private static final Logger log = Logger.getLogger( EmailApplicationService.class.getName() );
	public static final String NO_SERVICE = "ERROR [NO SERVICE PROVIDER UP!]";
	
	//Inject the pool of services
	@Inject
	ImmutableList<EmailServiceProvider<SimpleEmail>> providers;
	
	/**
	 * Process an email. Delegates to a third party email service.
	 * Basic fail-over implementation.
	 * 
	 * @see org.icervantes.uber.email.service.ApplicationService#process(org.icervantes.uber.email.model.SimpleEmail)
	 */
	public String process(SimpleEmail simpleEmail) {
		log.log(Level.INFO, String.format("Number of providers in the list: %s", providers.size()));
		
		for (EmailServiceProvider<SimpleEmail> p: providers) {
			log.log(Level.INFO, String.format("Inside provider's loop: %s", p.toString()));
			try{
				String response = p.send(simpleEmail);
				
				if (response.equals("ERROR")) {
					log.log(Level.SEVERE,String.format("(ERROR MESSAGE) Problems with service: %s", p.toString()));
				} else {
					return response;
				}
			} catch (Exception e) {
				log.log(Level.SEVERE,String.format("(CATCH Exception) Problems with service: %s", p.toString()));
				e.printStackTrace();
			} 
		}
		return NO_SERVICE;
	}
}
