package ucl.crest.bugzilla.miner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Generates the CSV file required as input for game-theoretic analysis. It uses the commons-csv library.
 *
 */
public class CsvExporter {
	/*
	 * 	private String issueKey; --
	private Resolution resolution; --
	private Status status; --
	private Priority prioriy; --
	private Date creationDate; --
	private User reportedBy; --
	private User resolvedBy; --
	private Date resolutionStart;?
	private Date resolverAssigned; --
	private Date resolverInProgress; --
	 */
	
	//Issue Key,Resolution,Status,Priority,Earliest Version,Latest Version,Earliest Fix Version,Latest Fix Version,Commits,Commits with Tags,Earliest Tag,JIRA/GitHub Distance,JIRA Distance,GitHub distance,Fix distance,JIRA Distance in Releases,GitHub Distance in Releases,Fix Distance in Releases,Creation Date,Closest Release JIRA,Closest Tag Git,Reported By,JIRA Resolved By,JIRA Resolver Start,JIRA Resolver Assignment,JIRA Resolver In Progress,JIRA Resolved Date,JIRA Resolution Time,Git Committer,Git Commit Date,Avg Lines,Git Resolution Time,Comments in JIRA,Priority Changer,Original Priority,New Priority,Git Repository,Total Deletions,Total Insertions,Avg Files,Change Log Size,Number of Reopens,Summary,Description,Project Key,Priority Change Date
	 private static final Object[] FILE_HEADER = {"Issue Key", "Resolution", "Status", "Priority", "Creation Date", "Reported By", "JIRA Resolved By", "JIRA Resolver Start" , "JIRA Resolver Assignment", "JIRA Resolver In Progress"};

	public static void generateCsv(File toCreate, Collection<BugReport> bugReports) throws IOException{
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader();
		FileWriter fileWriter = new FileWriter(toCreate);
		CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		
		csvFilePrinter.printRecord(FILE_HEADER);
		
		for(BugReport bugR : bugReports){
			List<Object> record = new ArrayList<Object>();
			record.add(bugR.getIssueKey());
			record.add(bugR.getResolution());
			record.add(bugR.getStatus());
			record.add(bugR.getPrioriy());
			record.add(bugR.getCreationDate());
			record.add(bugR.getReportedBy());
			record.add(bugR.getResolvedBy());
			record.add(bugR.getResolutionStart());
			record.add(bugR.getResolverAssigned());
			record.add(bugR.getResolverInProgress());
			
			csvFilePrinter.printRecord(record);
		}
		
		fileWriter.flush();
		fileWriter.close();
		csvFilePrinter.close();
		
	}

}
