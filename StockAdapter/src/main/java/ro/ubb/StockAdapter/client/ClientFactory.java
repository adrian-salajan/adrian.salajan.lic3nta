package ro.ubb.StockAdapter.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ClientFactory {
	
	private static Client instance;
	
	public static Client getClient() {
		if (instance == null) {
			ClientConfig config = new DefaultClientConfig();
			instance = Client.create(config);
		}
		return instance;
		
	}

}
