package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DistrictService {

	public static HedspiObject[] getDistrictsList(int city) {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_raw_districts_in_city", city));
	}

	public static int getCityOfDistrict(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_city_id_of_district", i);
		if (rs.size() == 0)
			return -1;
		if (rs.get(0).get("id") == null)
			return -1;
		return (int) rs.get(0).get("id");
	}

	public static HedspiObject getNewInCity(int i) {
		return CoreService.getInstance().firstSimpleResult(CoreService.getInstance().doQueryFunction("get_new_district_in_city", i));
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_district", i);
	}

	public static String rename(int id, String name) {
		return CoreService.getInstance().doUpdateFunction("update_district", id, name);
	}

	public static String getName(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_district_name", i);
		if (rs.isEmpty())
			return null;
		String name = (String) rs.get(0).get("name");
		if (name == null)
			name = "";
		return name;
	}

}
