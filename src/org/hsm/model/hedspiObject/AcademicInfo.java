package org.hsm.model.hedspiObject;

public class AcademicInfo {
	public AcademicInfo(HedspiObject teach, boolean isPassed, double result) {
		super();
		this.teach = teach;
		this.isPassed = isPassed;
		this.result = result;
	}

	private boolean isPassed;
	private double result;
	private HedspiObject teach;

	public boolean isPassed() {
		return isPassed;
	}

	public double getResult() {
		return result;
	}

	public HedspiObject getTeach() {
		return teach;
	}
}
