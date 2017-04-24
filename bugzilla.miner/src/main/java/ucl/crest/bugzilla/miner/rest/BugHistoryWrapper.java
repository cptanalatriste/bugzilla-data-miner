package ucl.crest.bugzilla.miner.rest;

import ucl.crest.bugzilla.miner.model.ChangeList;

public class BugHistoryWrapper {
	private ChangeList[] bugs;

	public ChangeList[] getBugs() {
		return bugs;
	}

	public void setBugs(ChangeList[] bugs) {
		this.bugs = bugs;
	}
	
	

}
