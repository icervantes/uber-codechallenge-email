package org.icervantes.uber.email.inject;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import junit.framework.TestCase;


/**
 * Test for <code>EmailServiceModule</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class EmailServiceModuleTest extends TestCase {
	@Test
	public void testModule() {
		Injector injector = Guice.createInjector(Stage.TOOL, new EmailServiceModule());
		assertNotNull(injector);
	}
}
