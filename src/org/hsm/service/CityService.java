package org.hsm.service;

import org.hsm.model.hedspiObject.HedspiObject;

public class CityService {

	public static HedspiObject[] getCitiesList() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction("get_city_list"));
	}

	public static HedspiObject getNew() {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction("get_new_city"));
	}

	public static String remove(int id) {
		return CoreService.getInstance().doUpdateFunction("delete_city", id);
	}

	public static String rename(int id, String new_name) {
		return CoreService.getInstance().doUpdateFunction("update_city", id,
				new_name);
	}
}
