package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class CityService {

	public static HedspiObject[] getCitiesList() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT cy, name FROM city\n" +
				"ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("cy");
			String name = (String)it.get("name");
			HedspiObject city = new HedspiObject(id, name);
			ret.add(city);
		}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO city DEFAULT VALUES RETURNING name, cy";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.size() == 0)
			return null;
		int id = (int)rs.get(0).get("cy");
		String name = (String)rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static String remove(int id) {
		String query = "DELETE FROM city WHERE cy = " + id;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int i, String string) {
		String query = "UPDATE City\n" +
				"SET name = '" + string.replace("'", "''") + "'\n" +
						"WHERE cy = " + i;
		return CoreService.getInstance().update(query);
	}
}
