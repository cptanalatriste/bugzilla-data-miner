package ucl.crest.bugzilla.miner;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		RestClient restClient = new RestClient(new Configuration());
		List<BugReport> bugReports = restClient.getLibreOfficeBugs();
		
		CsvExporter exporter = new CsvExporter();
		exporter.generateCsv();
	}
}
