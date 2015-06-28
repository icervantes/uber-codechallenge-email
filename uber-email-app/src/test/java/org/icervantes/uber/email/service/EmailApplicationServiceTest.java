package org.icervantes.uber.email.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.EmailServiceProvider;
import org.icervantes.uber.email.provider.SESEmailServiceProvider;
import org.icervantes.uber.email.provider.SendGridEmailServiceProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Test for <code>EmailApplicationService</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailApplicationServiceTest {
	@Mock
	SESEmailServiceProvider<SimpleEmail> ses;
	@Mock
	SendGridEmailServiceProvider<SimpleEmail> sg;
	@Mock
	ImmutableList<EmailServiceProvider<SimpleEmail>> providers;
	@Mock
	UnmodifiableIterator<EmailServiceProvider<SimpleEmail>> iterator;
	@Mock
	SimpleEmail email;
	@InjectMocks
	EmailApplicationService eas;

	@Test
	public void testHappyPath() {
		String response = "Happy!";
		SimpleEmail se = new SimpleEmail();
		when(ses.send(se)).thenReturn(response);
		when(sg.send(se)).thenReturn(response);
		when(providers.size()).thenReturn(2);
		when(providers.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.next()).thenReturn(ses);

		EmailApplicationService spy = spy(eas);
		String res = spy.process(se);

		assertEquals(response, res);
	}

	@Test
	public void testFailOverOne() {
		String response = "Happy!";
		SimpleEmail se = new SimpleEmail();
		when(ses.send(se)).thenReturn("ERROR");
		when(sg.send(se)).thenReturn(response);
		when(providers.size()).thenReturn(2);
		when(providers.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(ses).thenReturn(sg);

		EmailApplicationService spy = spy(eas);
		String res = spy.process(se);

		assertEquals(response, res);
	}

	@Test
	public void testFailOverTwo() {
		String response = "Happy!";
		SimpleEmail se = new SimpleEmail();
		when(ses.send(se)).thenReturn(response);
		when(sg.send(se)).thenReturn("ERROR");
		when(providers.size()).thenReturn(2);
		when(providers.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(ses).thenReturn(sg);

		EmailApplicationService spy = spy(eas);
		String res = spy.process(se);

		assertEquals(response, res);
	}

	@Test
	public void testERROR() {
		SimpleEmail se = new SimpleEmail();
		when(ses.send(se)).thenReturn("ERROR");
		when(sg.send(se)).thenReturn("ERROR");
		when(providers.size()).thenReturn(2);
		when(providers.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(ses).thenReturn(sg);

		EmailApplicationService spy = spy(eas);
		String res = spy.process(se);

		assertEquals(EmailApplicationService.NO_SERVICE, res);
	}
}
