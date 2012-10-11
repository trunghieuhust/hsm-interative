package org.hsm.model.hedspiObject;

public class HedspiObject implements Comparable<HedspiObject> {
	
	private int id;
	private String name;
	
	public HedspiObject(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public int compareTo(HedspiObject arg0) {
		return this.getId() - arg0.getId();
	}

	@Override
	public String toString() {
		return getName();
	}
}
