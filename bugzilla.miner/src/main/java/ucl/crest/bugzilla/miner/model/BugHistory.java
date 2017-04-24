package ucl.crest.bugzilla.miner.model;

import java.util.Date;

public class BugHistory {

	private Date when;
	private User who;
	private ChangeItem[] changes;
	
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public User getWho() {
		return who;
	}
	public void setWho(User who) {
		this.who = who;
	}
	public ChangeItem[] getChanges() {
		return changes;
	}
	public void setChanges(ChangeItem[] changes) {
		this.changes = changes;
	}
	
	
}
