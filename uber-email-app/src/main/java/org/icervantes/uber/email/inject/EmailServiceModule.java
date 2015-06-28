package org.icervantes.uber.email.inject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import org.icervantes.uber.email.inject.annotations.AmazonSES;
import org.icervantes.uber.email.inject.annotations.SendGridLabel;
import org.icervantes.uber.email.model.SimpleEmail;
import org.icervantes.uber.email.provider.EmailServiceProvider;
import org.icervantes.uber.email.provider.SESEmailServiceProvider;
import org.icervantes.uber.email.provider.SendGridEmailServiceProvider;
import org.icervantes.uber.email.provider.client.SESClientProvider;
import org.icervantes.uber.email.provider.client.SendGridAuth;
import org.icervantes.uber.email.provider.client.SendGridClientProvider;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.sendgrid.SendGrid;

/**
 * A Guice module with all the bindings for the email app.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class EmailServiceModule extends AbstractModule {
	private static final Logger log = Logger.getLogger(EmailServiceModule.class
			.getName());

	/**
	 * Guice setup.
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		try {
			Properties properties = new Properties();
			InputStream in = EmailServiceModule.class
					.getResourceAsStream("/system.properties");
			properties.load(in);
			in.close();
			Names.bindProperties(binder(), properties);
		} catch (IOException ex) {
			log.log(Level.SEVERE,
					"App was not able to load the properties file: system.properties");
		}

		bind(EmailServiceProvider.class).annotatedWith(AmazonSES.class).to(
				SESEmailServiceProvider.class);
		bind(EmailServiceProvider.class).annotatedWith(SendGridLabel.class).to(
				SendGridEmailServiceProvider.class);
		bind(AmazonSimpleEmailServiceClient.class).toProvider(
				SESClientProvider.class).in(Singleton.class);
		bind(SendGrid.class).toProvider(SendGridClientProvider.class).in(
				Singleton.class);
	}

	/**
	 * Provides a list with all the service providers.
	 * It can grow as need it. The order is given by <code>ImmutableList</code>.
	 * 
	 * @param providerOne
	 * @param providerTwo
	 * @return <code>ImmutableList<EmailServiceProvider<SimpleEmail>></code>
	 */
	@Provides
	ImmutableList<EmailServiceProvider<SimpleEmail>> getProviders(
			@AmazonSES EmailServiceProvider providerOne,
			@SendGridLabel EmailServiceProvider providerTwo) {
		ImmutableList<EmailServiceProvider<SimpleEmail>> list = ImmutableList
				.<EmailServiceProvider<SimpleEmail>> builder()
				.add(providerOne)
				.add(providerTwo)
				.build();
		return list;
	}

	@Provides
	@Singleton
	BasicAWSCredentials getAWSCredentials(
			@Named("aws_access_key_id") String user,
			@Named("aws_secret_access_key") String pass) {
		return new BasicAWSCredentials(user, pass);
	}
	
	@Provides
	@Singleton
	SendGridAuth getSendGridAuth(
			@Named("sg_username") String user,
			@Named("sg_pass") String pass) {
		return new SendGridAuth(user, pass);
	}
}
