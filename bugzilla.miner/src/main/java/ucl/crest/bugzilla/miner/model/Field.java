package ucl.crest.bugzilla.miner.model;

public class Field {

	private String value;

	public Field(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
