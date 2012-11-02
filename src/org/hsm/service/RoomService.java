package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class RoomService {

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_room", i);
	}

	public static HedspiObject getNew() {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction("get_new_room"));
	}

	public static HedspiObject[] getAll() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction("get_room_list"));
	}

	public static String getReloadName(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_room_name", i);
		if (rs.isEmpty())
			return null;
		return (String) rs.get(0).get("name");
	}

	public static String rename(int i, String string) {
		return CoreService.getInstance().doUpdateFunction("update_room", i,
				string);
	}

	public static int getNClassesIn(int id) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_n_classes_in_room", id);
		if (rs.isEmpty())
			return 0;
		return (int)rs.get(0).get("cnt");
	}

}
