package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DistrictService {

	public static HedspiObject[] getDistrictsList(int city) {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_raw_districts_in_city", city));
	}

	public static HedspiObject getNewInCity(int i) {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction(
						"get_new_district_in_city", i));
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_district", i);
	}

	public static String rename(int id, String name) {
		return CoreService.getInstance().doUpdateFunction("update_district",
				id, name);
	}

	public static String getName(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_district_name", i);
		if (rs.isEmpty())
			return null;
		String name = (String) rs.get(0).get("name");
		if (name == null)
			name = "";
		return name;
	}

	public static String[] getSingleStatistic(int i) {
		String[] ret = new String[4];
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_single_district_statistic", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> tt = rs.get(0);
		ret[0] = String.valueOf((int)tt.get("nlecturers"));
		ret[1] = String.valueOf((int)tt.get("nstudents"));
		ret[2] = (String)tt.get("best_student");
		ret[3] = (String)tt.get("worst_student");
		return ret;
	}

	public static HedspiObject getCity(int i) {
		return CoreService.getInstance().firstSimpleResult(CoreService.getInstance().doQueryFunction("get_city_of_district", i));
	}

}
