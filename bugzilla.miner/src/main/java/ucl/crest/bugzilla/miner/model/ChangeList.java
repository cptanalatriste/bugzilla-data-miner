package ucl.crest.bugzilla.miner.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeList {
	
	@JsonProperty("id")
	private String issueKey;
	
	@JsonProperty("history")
	private BugHistory[] bugHistory;
	
	public String getIssueKey() {
		return issueKey;
	}
	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}
	public BugHistory[] getBugHistory() {
		return bugHistory;
	}
	public void setBugHistory(BugHistory[] bugHistory) {
		this.bugHistory = bugHistory;
	}
	
	
}
