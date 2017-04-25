package ucl.crest.bugzilla.miner.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BugReport {



	@JsonProperty("id")
	private String issueKey;
	private Resolution resolution;
	private Status status;
	private Priority priority;

	@JsonProperty("creation_time")
	private Date creationDate;

	@JsonProperty("creator")
	private User reportedBy;
	private User resolvedBy;
	private Date resolutionDate;
	private Date resolverAssigned;
	private Date resolutionStart;
	
	private Date resolverInProgress;

	private User priorityChanger;
	private Priority originalPriority;
	private Priority newPriority;
	private Date priorityChange;

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority prioriy) {
		this.priority = prioriy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(User reportedBy) {
		this.reportedBy = reportedBy;
	}

	public User getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(User resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public Date getResolutionStart() {
		return resolutionStart;
	}

	public void setResolutionStart(Date resolutionStart) {
		this.resolutionStart = resolutionStart;
	}

	public Date getResolverAssigned() {
		return resolverAssigned;
	}

	public void setResolverAssigned(Date resolverAssigned) {
		this.resolverAssigned = resolverAssigned;
	}

	public Date getResolverInProgress() {
		return resolverInProgress;
	}

	public void setResolverInProgress(Date resolverInProgress) {
		this.resolverInProgress = resolverInProgress;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public User getPriorityChanger() {
		return priorityChanger;
	}

	public void setPriorityChanger(User priorityChanger) {
		this.priorityChanger = priorityChanger;
	}

	public Priority getOriginalPriority() {
		return originalPriority;
	}

	public void setOriginalPriority(Priority originalPriority) {
		this.originalPriority = originalPriority;
	}

	public Priority getNewPriority() {
		return newPriority;
	}

	public void setNewPriority(Priority newPriority) {
		this.newPriority = newPriority;
	}

	public Date getPriorityChange() {
		return priorityChange;
	}

	public void setPriorityChange(Date priorityChange) {
		this.priorityChange = priorityChange;
	}

}
