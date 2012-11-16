package org.hsm.model.hedspiObject;

public class Teach extends HedspiObject {
	private int room;
	private int course;
	private int lecturer;

	public Teach(int id, String code, int course, int lecturer, int room) {
		super(id, code);
		this.course = course;
		this.lecturer = lecturer;
		this.room = room;
	}

	public int getRoom() {
		return room;
	}

	public int getCourse() {
		return course;
	}

	public int getLecturer() {
		return lecturer;
	}

}
