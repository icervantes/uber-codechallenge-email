package org.icervantes.uber.email.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.client.SESClientProvider;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

/**
 * Amazon SES email service provider implementation.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class SESEmailServiceProvider<T extends SimpleEmail> implements
		EmailServiceProvider<T> {
	private static final Logger log = Logger.getLogger( SESEmailServiceProvider.class.getName() );
	
	@Inject
	SESClientProvider client;

	/**
	 * @see org.icervantes.uber.email.provider.EmailServiceProvider#send(org.icervantes.uber.email.model.SimpleEmail)
	 */
	@Override
	public String send(T t) {
		AmazonSimpleEmailServiceClient amazonService = client.get();
		// Construct an object to contain the recipient address.
		Destination destination = new Destination()
				.withToAddresses(new String[] { t.getTo() });

		// Create the subject and body of the message.
		Content subject = new Content().withData(t.getSubject());
		Content textBody = new Content().withData(t.body);
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(t.from)
				.withDestination(destination).withMessage(message);
		try {
			SendEmailResult result = amazonService.sendEmail(request);
			return result.toString();
		} catch (AmazonClientException e) {
			log.log(Level.SEVERE, 
					String.format("Error calling Amazon WS: %s",
							e.getMessage()));
		} catch (Exception e) {
			log.log(Level.SEVERE, 
					String.format("Error calling Amazon WS: %s",
							e.getMessage()));
		} 
		return "ERROR";
	}
}
