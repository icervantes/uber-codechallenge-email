package org.icervantes.uber.email.provider.client;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;

/**
 * Test for <code>SESClientProvider</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SESClientProviderTest {
	@Mock
	BasicAWSCredentials cred;
	@Mock
	AmazonSimpleEmailServiceClient client;
	@InjectMocks
	private SESClientProvider provider;
	
	@Test
	public void getClientTest() {
		SESClientProvider spy = spy(provider);
		when(spy.buildClient(cred)).thenReturn(client);
		
		AmazonSimpleEmailServiceClient res = spy.get();
		
		assertEquals(client, res);
	}
}
