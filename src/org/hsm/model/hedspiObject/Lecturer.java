package org.hsm.model.hedspiObject;

import java.util.Date;

public class Lecturer extends Contact {
	
	private int degree;
	private int faculty;
	
	public Lecturer(int id, String first, String last, boolean isMale,
			Date dob, String[] emails, String[] phones, String note,
			String home, int district, int degree, int faculty) {
		super(id, first, last, isMale, dob, emails, phones, note, home,
				district);
		this.degree = degree;
		this.faculty = faculty;
	}

	public int getDegree() {
		return degree;
	}

	public int getFaculty() {
		return faculty;
	}

}
