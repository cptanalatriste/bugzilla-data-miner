package ucl.crest.bugzilla.miner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ucl.crest.bugzilla.miner.model.BugReport;

/**
 * Generates the CSV file required as input for game-theoretic analysis. It uses
 * the commons-csv library.
 *
 */
public class CsvExporter {

	private static final Object[] FILE_HEADER = { "Issue Key", "Resolution", "Status", "Priority", "Creation Date",
			"Reported By", "JIRA Resolved By", "JIRA Resolver Start", "JIRA Resolver Assignment",
			"JIRA Resolver In Progress", "JIRA Resolved Date", "Priority Changer", "Original Priority", "New Priority",
			"Priority Change Date" };

	private static final String NEW_LINE_SEPARATOR = "\n";

	public static void generateCsv(File toCreate, Collection<BugReport> bugReports) throws IOException {
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		FileWriter fileWriter = new FileWriter(toCreate);
		CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

		csvFilePrinter.printRecord(FILE_HEADER);

		for (BugReport bugReport : bugReports) {
			List<Object> record = new ArrayList<Object>();
			record.add(bugReport.getIssueKey());
			record.add(bugReport.getResolution());
			record.add(bugReport.getStatus());
			record.add(bugReport.getPriority());
			record.add(bugReport.getCreationDate());
			record.add(bugReport.getReportedBy());
			record.add(bugReport.getResolvedBy());
			record.add(bugReport.getResolutionStart());
			record.add(bugReport.getResolverAssigned());
			record.add(bugReport.getResolverInProgress());

			record.add(bugReport.getResolutionDate());
			record.add(bugReport.getPriorityChanger());
			record.add(bugReport.getOriginalPriority());
			record.add(bugReport.getNewPriority());
			record.add(bugReport.getPriorityChange());

			csvFilePrinter.printRecord(record);
		}

		fileWriter.flush();
		fileWriter.close();
		csvFilePrinter.close();

	}

}
