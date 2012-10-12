package org.hsm.model.hedspiObject;

import java.util.ArrayList;
import java.util.Date;

public class Student extends Contact {
	private double point;
	private String mssv;
	private int year;
	private int hedspiClass;

	public Student(int id, String first, String last,
			boolean isMale, Date dob, ArrayList<String> emails,
			ArrayList<String> phones, String note, String home, int district,
			double point, String mssv, int year, int hedspiClass) {
		super(id, first, last, isMale, dob, emails, phones, note, home,
				district);
		this.point = point;
		this.mssv = mssv;
		this.year = year;
		this.hedspiClass = hedspiClass;
	}

	public double getPoint() {
		return point;
	}

	public String getMssv() {
		return mssv;
	}

	public int getYear() {
		return year;
	}

	public int getHedspiClass() {
		return hedspiClass;
	}
}
