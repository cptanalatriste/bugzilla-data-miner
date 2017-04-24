package ucl.crest.bugzilla.miner.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ChangeItem {
	@JsonProperty("field_name")
	private Field field;
	private String added;

	@JsonProperty("attachment_id")
	private int attachmentId;
	private String removed;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getAdded() {
		return added;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

}
