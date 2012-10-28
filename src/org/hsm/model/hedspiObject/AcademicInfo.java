package org.hsm.model.hedspiObject;

public class AcademicInfo {
	private HedspiObject course;
	private HedspiObject room;
	private HedspiObject lecturer;
	private boolean isPassed;
	private double result;

	public AcademicInfo(HedspiObject course, HedspiObject lecturer,
			HedspiObject room, boolean isPassed, double result) {
		super();
		this.course = course;
		this.room = room;
		this.lecturer = lecturer;
		this.isPassed = isPassed;
		this.result = result;
	}

	public HedspiObject getCourse() {
		return course;
	}

	public HedspiObject getRoom() {
		return room;
	}

	public HedspiObject getLecturer() {
		return lecturer;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public double getResult() {
		return result;
	}
}
