package org.hsm.model.hedspiObject;

public class HedspiObjectWithNote extends HedspiObject {
	
	private String note;

	public HedspiObjectWithNote(int id, String name, String note) {
		super(id, name);
		this.note = note;
	}

	public String getNote() {
		return note;
	}

}
