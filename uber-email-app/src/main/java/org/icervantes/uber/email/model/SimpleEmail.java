package org.icervantes.uber.email.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Domain object for an email object abstraction.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@XmlRootElement
public class SimpleEmail {
	@XmlTransient
	public String from;
	@XmlTransient
	public String to;
	@XmlTransient
	public String body;
	@XmlTransient
	public String subject;

	//Validates an email address
	EmailValidator ev = EmailValidator.getInstance();

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = checkNotNull(from);
		checkArgument(ev.isValid(from));
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = checkNotNull(to);
		checkArgument(ev.isValid(to));
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = checkNotNull(body);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = checkNotNull(subject);
	}

	@Override
	public String toString() {
		return "From: " + this.from + ", To: " + this.to + ", Subject: " + this.subject + ", Body: " + this.body;
	}

}
