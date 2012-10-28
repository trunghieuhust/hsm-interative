package org.hsm.model.hedspiObject;

public class Course extends HedspiObject {

	private double nFees;
	private int nCredits;
	private String topic;
	private double time;
	private String note;
	private String code;

	public Course(int id, String name, double nFees, int nCredits,
			String topic, double time, String note, String code) {
		super(id, name);
		this.nFees = nFees;
		this.nCredits = nCredits;
		this.topic = topic;
		this.time = time;
		this.note = note;
		this.code = code;
	}

	public double getNFees() {
		return nFees;
	}

	public int getNCredits() {
		return nCredits;
	}

	public String getTopic() {
		return topic;
	}

	public double getTime() {
		return time;
	}

	public String getNote() {
		return note;
	}

	public String getCode() {
		return code;
	}

}
