package ucl.crest.bugzilla.miner.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jersey.api.client.Client;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import ucl.crest.bugzilla.miner.Configuration;
import ucl.crest.bugzilla.miner.model.BugReport;
import ucl.crest.bugzilla.miner.model.HistoryExplorer;

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

		List<BugReport> bugReports = new ArrayList<BugReport>();
		for (String component : configuration.getComponentCatalog()) {
			int limit = 10;

			try {
				String componentReportsUrl = configuration.getAllBugsResource(limit, component);
				WebResource componentsReportsResource = client.resource(componentReportsUrl);

				System.out.println("componentReportsUrl: " + componentReportsUrl);
				BugReportListWrapper componentReportsResponse = componentsReportsResource.accept(JSON_MEDIA_TYPE)
						.get(BugReportListWrapper.class);

				System.out.println(
						"componentReportsResponse.getBugs().length: " + componentReportsResponse.getBugs().length);
				for (BugReport report : componentReportsResponse.getBugs()) {
					String issueKey = report.getIssueKey();

					String bugHistoryUrl = configuration.getBugHistoryResource(issueKey);
					System.out.println("bugHistoryUrl: " + bugHistoryUrl);

					WebResource bugHistoryResource = client.resource(bugHistoryUrl);
					BugHistoryWrapper bugHistoryResponse = bugHistoryResource.accept(JSON_MEDIA_TYPE)
							.get(BugHistoryWrapper.class);

					HistoryExplorer historyExplorer = new HistoryExplorer(report);
					historyExplorer.applyBugHistory(bugHistoryResponse.getBugs()[0].getBugHistory());

					System.out.println("Parsed bug: \n" + report);

				}

				bugReports.addAll(Arrays.asList(componentReportsResponse.getBugs()));
			} catch (Exception e) {
				//TODO: Implement a fallback method based on a limit value. We now that 1000 works
				System.out.println("ERROR: Couldn't retrieve reports for component " + component);
				e.printStackTrace();
			}
		}

		return bugReports;
	}

}
