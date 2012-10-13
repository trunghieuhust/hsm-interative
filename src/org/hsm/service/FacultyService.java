package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class FacultyService {

	public static HedspiObject getNew() {
		String query = "INSERT INTO \"Faculty\" DEFAULT VALUES RETURNING \"FC#\", \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.size() == 0)
			return null;
		int id = (int)rs.get(0).get("FC#");
		String name = (String)rs.get(0).get("Name");
		return new HedspiObject(id, name);
	}

	public static String remove(int i) {
		String query = "DELETE FROM \"Faculty\" WHERE \"FC#\" = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject[] getFacutiesList() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"FC#\", \"Name\" FROM \"Faculty\" ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs)
			if (it.get("FC#") != null){
				int id = (int)it.get("FC#");
				String name = (String)it.get("Name");
				ret.add(new HedspiObject(id, name));
			}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String rename(int i, String string) {
		String query = "UPDATE \"Faculty\"\n" +
				"SET \"Name\" = '" + string.replace("'", "''") + "'\n" +
						"\"FC#\" = " + i;
		return CoreService.getInstance().update(query);
	}

}
