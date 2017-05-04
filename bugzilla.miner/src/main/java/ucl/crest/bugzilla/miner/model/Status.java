package ucl.crest.bugzilla.miner.model;

public class Status {

	private String value;

	public Status(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
