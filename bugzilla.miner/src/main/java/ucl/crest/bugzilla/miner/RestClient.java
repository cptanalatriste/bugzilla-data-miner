package ucl.crest.bugzilla.miner;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Get bug information from BugZilla. Relies heavily on Jersey.
 *
 */
public class RestClient {
	
	private static final int HTTP_SUCCESS = 200;
	private static String JSON_MEDIA_TYPE = "application/json";
	
	private Configuration configuration;
	
	public RestClient(Configuration configuration) {
		this.configuration = configuration;
	}

	public List<BugReport> getLibreOfficeBugs() {
		
		Client client = Client.create();
		
		int limit = 10;
		WebResource webResouce = client.resource(configuration.getAllBugsResource(limit));
		ClientResponse response = webResouce.accept(JSON_MEDIA_TYPE).get(ClientResponse.class);
		
		if (response.getStatus()  != HTTP_SUCCESS) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		String output = response.getEntity(String.class);
		System.out.println("output: " + output);
		
		List<BugReport> bugReports = new ArrayList<BugReport>();
		
		return bugReports;
	}

}
