package ucl.crest.bugzilla.miner;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BugReportWrapper {
	private BugReport[] bugs;

	public BugReport[] getBugs() {
		return bugs;
	}

	public void setBugs(BugReport[] bugs) {
		this.bugs = bugs;
	}
	
	
}
