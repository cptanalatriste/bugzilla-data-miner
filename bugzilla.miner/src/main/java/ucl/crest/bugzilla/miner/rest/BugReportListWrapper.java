package ucl.crest.bugzilla.miner.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import ucl.crest.bugzilla.miner.model.BugReport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BugReportListWrapper {
	private BugReport[] bugs;

	public BugReport[] getBugs() {
		return bugs;
	}

	public void setBugs(BugReport[] bugs) {
		this.bugs = bugs;
	}

}
