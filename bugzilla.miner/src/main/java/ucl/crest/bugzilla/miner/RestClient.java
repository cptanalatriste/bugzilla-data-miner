package ucl.crest.bugzilla.miner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Get bug information from BugZilla. Relies heavily on Jersey.
 *
 */
public class RestClient {

	private static String JSON_MEDIA_TYPE = "application/json";

	private Configuration configuration;

	public RestClient(Configuration configuration) {
		this.configuration = configuration;
	}

	public List<BugReport> getLibreOfficeBugs() {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		int limit = 10;

		String url = configuration.getAllBugsResource(limit);
		WebResource webResouce = client.resource(url);

		System.out.println("url: " + url);
		BugReportWrapper response = webResouce.accept(JSON_MEDIA_TYPE).get(BugReportWrapper.class);

		for (BugReport report : response.getBugs()) {
			System.out.println("report.getId(): " + report.getId());
			System.out.println("report.getResolution(): " + report.getResolution());
			System.out.println("report.getPriority(): " + report.getPriority());


		}

		List<BugReport> bugReports = new ArrayList<BugReport>(Arrays.asList(response.getBugs()));
		return bugReports;
	}

}
