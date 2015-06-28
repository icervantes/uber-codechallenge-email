package org.icervantes.uber.email.service;

import org.icervantes.uber.email.model.SimpleEmail;

/**
 * Defines the contract to process an email.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public interface ApplicationService {
	String process(SimpleEmail simpleEmail);
}
