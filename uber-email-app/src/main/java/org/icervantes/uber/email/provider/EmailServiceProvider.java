package org.icervantes.uber.email.provider;

import org.icervantes.uber.email.model.SimpleEmail;

/**
 * Defines the contract for any email provider.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 * @param <T>
 */
public interface EmailServiceProvider<T extends SimpleEmail> {
	String send(T t);
}
