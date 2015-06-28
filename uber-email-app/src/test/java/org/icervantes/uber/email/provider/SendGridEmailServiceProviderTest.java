package org.icervantes.uber.email.provider;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.SendGridEmailServiceProvider;
import org.icervantes.uber.email.provider.client.SendGridClientProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

/**
 * Test for <code>SendGridEmailServiceProvider</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SendGridEmailServiceProviderTest {
	@Mock
	SendGridClientProvider client;
	@Mock
	SendGrid sendGrid;
	@Mock
	SendGrid.Response response;
	@InjectMocks
	SendGridEmailServiceProvider<SimpleEmail> provider;
	
	@Test
	public void successSendEmailTest() throws SendGridException {
		when(response.getMessage()).thenReturn("Email sent!");
		when(sendGrid.send(Matchers.any(SendGrid.Email.class))).thenReturn(response);
		when(client.get()).thenReturn(sendGrid);
		
		SendGridEmailServiceProvider<SimpleEmail> spy = spy(provider);
		String res = spy.send(new SimpleEmail());
		
		assertEquals("Email sent!", res);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void failedSendEmailTest() throws SendGridException {
		when(sendGrid.send(Matchers.any(SendGrid.Email.class))).thenThrow(SendGridException.class);
		when(client.get()).thenReturn(sendGrid);
		
		SendGridEmailServiceProvider<SimpleEmail> spy = spy(provider);
		String res = spy.send(new SimpleEmail());
		
		assertEquals("ERROR", res);
	}
}
