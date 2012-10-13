package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class CityService {

	public static HedspiObject[] getCitiesList() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"CY#\", \"Name\" FROM \"City\"\n" +
				"ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("CY#");
			String name = (String)it.get("Name");
			if (name == null)
				name = "";
			HedspiObject city = new HedspiObject(id, name);
			ret.add(city);
		}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO \"City\" DEFAULT VALUES RETURNING \"Name\", \"CY#\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.size() == 0)
			return null;
		int id = (int)rs.get(0).get("CY#");
		String name = (String)rs.get(0).get("Name");
		if (name == null)
			name = "";
		return new HedspiObject(id, name);
	}

	public static String remove(int id) {
		String query = "DELETE FROM \"City\" WHERE \"CY#\" = " + id;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int i, String string) {
		String query = "UPDATE \"City\"\n" +
				"SET \"Name\" = '" + string.replace("'", "''") + "'\n" +
						"WHERE \"CY#\" = " + i;
		return CoreService.getInstance().update(query);
	}
}
