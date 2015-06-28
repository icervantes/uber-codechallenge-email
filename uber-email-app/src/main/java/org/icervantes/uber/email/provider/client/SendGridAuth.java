package org.icervantes.uber.email.provider.client;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stores SenGrid's authentication data.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public final class SendGridAuth {
	private final String user;
	private final String pass;
	
	public SendGridAuth(String user, String pass) {
		this.user = checkNotNull(user);
		this.pass = checkNotNull(pass);
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPass() {
		return this.pass;
	}
	
	@Override
	public String toString() {
		return String.format("User: %s - Pass: %s", this.user, this.pass);
	}
}
