package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class CityService {

	public static HedspiObject[] getCitiesList() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_city_list");
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("id");
			String name = (String)it.get("name");
			HedspiObject city = new HedspiObject(id, name);
			ret.add(city);
		}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static HedspiObject getNew() {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_new_city");
		if (rs.size() == 0)
			return null;
		int id = (int)rs.get(0).get("id");
		String name = (String)rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static String remove(int id) {
		return CoreService.getInstance().doUpdateFunction("delete_city", id);
	}

	public static String rename(int id, String new_name) {
		return CoreService.getInstance().doUpdateFunction("update_city", id, new_name);
	}
}
