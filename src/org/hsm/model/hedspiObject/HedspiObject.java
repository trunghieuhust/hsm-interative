package org.hsm.model.hedspiObject;

public class HedspiObject implements Comparable<HedspiObject> {

	private int id;
	private String name;

	/**
	 * Tạo một hedspi object mới. Nếu name rỗng (<code>null</code>) thì sẽ tự
	 * động gán bằng xâu rỗng.
	 * 
	 * @param id
	 *            chỉ số
	 * @param name
	 *            tên, có thể là <code>null</code>
	 */
	public HedspiObject(int id, String name) {
		super();
		this.id = id;
		if (name == null)
			name = "";
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

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		HedspiObject val = (HedspiObject) obj;
		if (val == null)
			return false;
		return getId() == val.getId();
	}
}
