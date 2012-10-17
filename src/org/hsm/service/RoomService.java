package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class RoomService {

	public static String remove(int i) {
		String query = "DELETE FROM room WHERE rm = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO room DEFAULT VALUES RETURNING rm, name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.isEmpty() || rs.get(0).get("rm") == null)
			return null;
		int id = (int) rs.get(0).get("rm");
		String name = (String) rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT rm, name FROM room ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		for (HashMap<String, Object> it : rs)
			if (it.get("rm") != null) {
				int id = (int) it.get("rm");
				String name = (String) it.get("name");
				ret.add(new HedspiObject(id, name));
			}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String getReloadName(int i) {
		String query = "SELECT name FROM room WHERE rm = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.isEmpty())
			return null;
		return (String) rs.get(0).get("name");
	}

	public static String rename(int i, String string) {
		String query = "UPDATE room\n" + "SET name = '"
				+ string.replace("'", "''") + "'\n" + "WHERE rm = " + i;
		return CoreService.getInstance().update(query);
	}

}
