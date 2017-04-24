package ucl.crest.bugzilla.miner.model;

public class User {

	private String value;

	public User(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
