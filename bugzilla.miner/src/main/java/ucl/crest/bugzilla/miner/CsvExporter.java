package ucl.crest.bugzilla.miner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
	
	private Date resolutionDate; --
	private double resolutionTime; -- 
	private User priorityChanger; -- 
	private Priority originalPriority; --
	private Priority newPriority; --
	private Date priorityChange;
	 *
	 *
	 */
	
	
	
	//Issue Key,Resolution,Status,Priority,Earliest Version,Latest Version,Earliest Fix Version,Latest Fix Version,Commits,Commits with Tags,Earliest Tag,JIRA/GitHub Distance,JIRA Distance,GitHub distance,Fix distance,JIRA Distance in Releases,GitHub Distance in Releases,Fix Distance in Releases,Creation Date,Closest Release JIRA,Closest Tag Git,Reported By,JIRA Resolved By,JIRA Resolver Start,JIRA Resolver Assignment,JIRA Resolver In Progress,JIRA Resolved Date,JIRA Resolution Time,Git Committer,Git Commit Date,Avg Lines,Git Resolution Time,Comments in JIRA,Priority Changer,Original Priority,New Priority,Git Repository,Total Deletions,Total Insertions,Avg Files,Change Log Size,Number of Reopens,Summary,Description,Project Key,Priority Change Date
	 private static final Object[] FILE_HEADER = {"Issue Key", "Resolution", "Status", "Priority", "Creation Date", "Reported By", "JIRA Resolved By", "JIRA Resolver Start" , "JIRA Resolver Assignment", "JIRA Resolver In Progress", "JIRA Resolved Date", "Git Resolution Time", "Priority Changer", "Original Priority" , "New Priority", "Priority Change Date"};

	public static void generateCsv(File toCreate, Collection<BugReport> bugReports) throws IOException{
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader();
		FileWriter fileWriter = new FileWriter(toCreate);
		CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		
		csvFilePrinter.printRecord(FILE_HEADER);
		
		for(BugReport bugR : bugReports){
			List<Object> record = new ArrayList<Object>();
			record.add(bugR.getIssueKey());
			record.add(bugR.getResolution().toString());
			record.add(bugR.getStatus().toString());
			record.add(bugR.getPrioriy().toString());
			record.add(bugR.getCreationDate().toString());
			record.add(bugR.getReportedBy().toString());
			record.add(bugR.getResolvedBy().toString());
			record.add(bugR.getResolutionStart().toString());
			record.add(bugR.getResolverAssigned().toString());
			record.add(bugR.getResolverInProgress().toString());
			
			record.add(bugR.getResolutionDate().toString());
			record.add(Double.toString(bugR.getResolutionTime()));
			record.add(bugR.getPriorityChanger().toString());
			record.add(bugR.getOriginalPriority().toString());
			record.add(bugR.getNewPriority().toString());
			record.add(bugR.getPriorityChange().toString());
			
			csvFilePrinter.printRecord(record);
		}
		
		fileWriter.flush();
		fileWriter.close();
		csvFilePrinter.close();
		
	}

}
