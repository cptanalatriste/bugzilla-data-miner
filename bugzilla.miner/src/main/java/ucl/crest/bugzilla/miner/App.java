package ucl.crest.bugzilla.miner;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The project entry point.
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Connecting to BugZilla REST API ...");

		RestClient restClient = new RestClient(new Configuration());
		List<BugReport> bugReports = restClient.getLibreOfficeBugs();
		
		try {
			CsvExporter.generateCsv(new File("test.csv"), bugReports);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
