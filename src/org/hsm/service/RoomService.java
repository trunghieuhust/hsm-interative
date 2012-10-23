package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class RoomService {

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_room", i);
	}

	public static HedspiObject getNew() {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_new_room");
		if (rs.isEmpty() || rs.get(0).get("id") == null)
			return null;
		int id = (int) rs.get(0).get("id");
		String name = (String) rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_room_list");
		for (HashMap<String, Object> it : rs)
			if (it.get("id") != null) {
				int id = (int) it.get("id");
				String name = (String) it.get("name");
				ret.add(new HedspiObject(id, name));
			}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String getReloadName(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_room_name", i);
		if (rs.isEmpty())
			return null;
		return (String) rs.get(0).get("name");
	}

	public static String rename(int i, String string) {
		return CoreService.getInstance().doUpdateFunction("update_room", i, string);
	}

}
