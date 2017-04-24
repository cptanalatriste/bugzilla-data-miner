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

		String allReportsUrl = configuration.getAllBugsResource(limit);
		WebResource alLreportsResource = client.resource(allReportsUrl);

		System.out.println("allReportsUrl: " + allReportsUrl);
		BugReportListWrapper allReportsResponse = alLreportsResource.accept(JSON_MEDIA_TYPE)
				.get(BugReportListWrapper.class);

		for (BugReport report : allReportsResponse.getBugs()) {
			System.out.println("report.getId(): " + report.getIssueKey());
			System.out.println("report.getResolution(): " + report.getResolution());
			System.out.println("report.getPriority(): " + report.getPriority());
			System.out.println("report.getPriority(): " + report.getCreationDate());
			System.out.println("report.getReportedBy(): " + report.getReportedBy());

			String bugHistoryUrl = configuration.getBugHistoryResource(report.getIssueKey());
			System.out.println("bugHistoryUrl: " + bugHistoryUrl);

			WebResource bugHistoryResource = client.resource(bugHistoryUrl);
			BugHistoryWrapper bugHistoryResponse = bugHistoryResource.accept(JSON_MEDIA_TYPE)
					.get(BugHistoryWrapper.class);

			report.applyBugHistory(bugHistoryResponse.getBugs()[0].getBugHistory());

			System.out.println("report.getResolvedBy(): " + report.getResolvedBy());
			System.out.println("report.getResolutionDate(): " + report.getResolutionDate());
			System.out.println("report.getResolverAssigned(): " + report.getResolverAssigned());

		}

		List<BugReport> bugReports = new ArrayList<BugReport>(Arrays.asList(allReportsResponse.getBugs()));
		return bugReports;
	}

}
