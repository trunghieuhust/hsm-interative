package org.hsm.model.hedspiObject;

import java.util.ArrayList;
import java.util.Date;

public class Contact extends HedspiObject {
	private String first;
	private String last;
	private boolean isMale;
	private Date dob;
	private ArrayList<String> emails;
	private ArrayList<String> phones;
	private String note;
	private String home;
	private int district;

	public Contact(int id, String first, String last,
			boolean isMale, Date dob, ArrayList<String> emails,
			ArrayList<String> phones, String note, String home, int district) {
		super(id, first + " " + last);
		this.first = first;
		this.last = last;
		this.isMale = isMale;
		this.dob = dob;
		this.emails = emails;
		this.phones = phones;
		this.note = note;
		this.home = home;
		this.district = district;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public boolean isMale() {
		return isMale;
	}

	public Date getDob() {
		return dob;
	}

	public ArrayList<String> getEmails() {
		return emails;
	}

	public ArrayList<String> getPhones() {
		return phones;
	}

	public String getNote() {
		return note;
	}

	public String getHome() {
		return home;
	}

	public int getDistrict() {
		return district;
	}
}
