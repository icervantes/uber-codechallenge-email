package org.icervantes.uber.email.provider.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.google.inject.Provider;

/**
 * Provider of <code>AmazonSimpleEmailServiceClient</code>.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
public class SESClientProvider implements
		Provider<AmazonSimpleEmailServiceClient> {
	private static final Logger log = Logger.getLogger( SESClientProvider.class.getName() );

	@Inject
	private BasicAWSCredentials cred;

	/**
	 * Provides an instance of AmazonSimpleEmailServiceClient
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	@Override
	public AmazonSimpleEmailServiceClient get() {
		AmazonSimpleEmailServiceClient client = null;
		try {
			client = buildClient(cred);
			Region REGION = Region.getRegion(Regions.US_WEST_2);
			client.setRegion(REGION);
			return client;
		} catch (Exception e) {
			log.log(Level.SEVERE, 
					String.format("Error building Amazon client: %s",
							e.getMessage()));
		}
		return client;
	}

	protected AmazonSimpleEmailServiceClient buildClient(
			BasicAWSCredentials cred) {
		return new AmazonSimpleEmailServiceClient(cred);
	}
}
