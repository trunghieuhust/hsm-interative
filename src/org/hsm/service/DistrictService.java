package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DistrictService {

	public static HedspiObject[] getDistrictsList(int city) {
		String query = "SELECT dt, name FROM district\n"
				+ "WHERE cy = " + city + "\n" + "ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for (HashMap<String, Object> it : rs) {
			int id = (int) it.get("dt");
			String name = (String) it.get("name");
			if (name == null)
				name = "";
			HedspiObject district = new HedspiObject(id, name);
			ret.add(district);
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static int getCityOfDistrict(int i) {
		String query = "SELECT cy FROM district WHERE dt = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.size() == 0)
			return -1;
		if (rs.get(0).get("cy") == null)
			return -1;
		return (int) rs.get(0).get("cy");
	}

	public static HedspiObject getNewInCity(int i) {
		String query = "INSERT INTO district (cy) VALUES (" + i
				+ ") RETURNING dt, name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.size() == 0)
			return null;
		int id = (int) rs.get(0).get("dt");
		String name = (String) rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static String remove(int i) {
		String query = "DELETE FROM district WHERE dt = " + i;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int id, String name) {
		String query = "UPDATE district\n" + "SET name = '"
				+ name.replace("'", "''") + "'\n" + "WHERE dt = " + id;
		return CoreService.getInstance().update(query);
	}

	public static String getName(int i) {
		String query = "SELECT name FROM district WHERE dt = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.size() == 0)
			return null;
		String name = (String) rs.get(0).get("name");
		if (name == null)
			name = "";
		return name;
	}

}
