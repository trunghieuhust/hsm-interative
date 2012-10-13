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
}
