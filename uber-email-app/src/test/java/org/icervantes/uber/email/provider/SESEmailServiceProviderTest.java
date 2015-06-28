package org.icervantes.uber.email.provider;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.client.SESClientProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

/**
 * Test for <code>SESEmailServiceProvider</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SESEmailServiceProviderTest {
	@Mock
	SESClientProvider client;
	@Mock
	AmazonSimpleEmailServiceClient amazonService;
	@Mock
	SendEmailResult result;
	@InjectMocks
	SESEmailServiceProvider<SimpleEmail> provider;
	
	@Test
	public void successSendEmailTest() {
		when(result.toString()).thenReturn("Email sent!");
		when(amazonService.sendEmail(Matchers.any(SendEmailRequest.class))).thenReturn(result);
		when(client.get()).thenReturn(amazonService);
		
		SESEmailServiceProvider<SimpleEmail> spy = spy(provider);
		String res = spy.send(new SimpleEmail());
		
		assertEquals("Email sent!", res);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void failedSendEmailTest() {
		when(result.toString()).thenReturn("Email sent!");
		when(amazonService.sendEmail(Matchers.any(SendEmailRequest.class))).
			thenThrow(Exception.class);
		when(client.get()).thenReturn(amazonService);
		
		SESEmailServiceProvider<SimpleEmail> spy = spy(provider);
		String res = spy.send(new SimpleEmail());
		
		assertEquals("ERROR", res);
	}
}
