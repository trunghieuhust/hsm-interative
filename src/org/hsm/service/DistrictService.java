package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DistrictService {

	public static HedspiObject[] getDistrictsList(int city) {
		String query = "SELECT \"DT#\", \"Name\" FROM \"District\"\n"
				+ "WHERE \"CY#\" = " + city + "\n" + "ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for (HashMap<String, Object> it : rs) {
			int id = (int) it.get("DT#");
			String name = (String) it.get("Name");
			if (name == null)
				name = "";
			HedspiObject district = new HedspiObject(id, name);
			ret.add(district);
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static int getCityOfDistrict(int i) {
		String query = "SELECT \"CY#\" FROM \"District\" WHERE \"DT#\" = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		if (rs.size() == 0)
			return -1;
		if (rs.get(0).get("CY#") == null)
			return -1;
		return (int) rs.get(0).get("CY#");
	}

}
