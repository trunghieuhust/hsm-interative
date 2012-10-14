package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class RoomService {

	public static String remove(int i) {
		String query = "DELETE FROM \"Room\" WHERE \"RM#\" = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO \"Room\" DEFAULT VALUES RETURNING \"RM#\", \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.isEmpty() || rs.get(0).get("RM#") == null)
			return null;
		int id = (int) rs.get(0).get("RM#");
		String name = (String) rs.get(0).get("Name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"RM#\", \"Name\" FROM \"Room\" ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		for (HashMap<String, Object> it : rs)
			if (it.get("RM#") != null) {
				int id = (int) it.get("RM#");
				String name = (String) it.get("Name");
				ret.add(new HedspiObject(id, name));
			}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String getReloadName(int i) {
		String query = "SELECT \"Name\" FROM \"Room\" WHERE \"RM#\" = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.isEmpty())
			return null;
		return (String) rs.get(0).get("Name");
	}

	public static String rename(int i, String string) {
		String query = "UPDATE \"Room\"\n" + "SET \"Name\" = '"
				+ string.replace("'", "''") + "'\n" + "WHERE \"RM#\" = " + i;
		return CoreService.getInstance().update(query);
	}

}
