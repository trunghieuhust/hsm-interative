package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class FacultyService {

	public static HedspiObject getNew() {
		String query = "INSERT INTO faculty DEFAULT VALUES RETURNING fc, name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.size() == 0)
			return null;
		int id = (int)rs.get(0).get("fc");
		String name = (String)rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static String remove(int i) {
		String query = "DELETE FROM faculty WHERE fc = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject[] getFacutiesList() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT fc, name FROM faculty ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs)
			if (it.get("fc") != null){
				int id = (int)it.get("fc");
				String name = (String)it.get("name");
				ret.add(new HedspiObject(id, name));
			}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String rename(int i, String string) {
		String query = "UPDATE faculty\n" +
				"SET name = '" + string.replace("'", "''") + "'\n" +
						"fc = " + i;
		return CoreService.getInstance().update(query);
	}

}
