package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class ClassService {

	public static HedspiObject newClass() {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_new_class");
		int cl = (int)rs.get(0).get("id");
		String name = (String)rs.get(0).get("name");
		HedspiObject cla = new HedspiObject(cl, name);
		return cla;
	}

	public static String remove(int cl){
		return CoreService.getInstance().doUpdateFunction("delete_class", cl);
	}

	public static String rename(int id, String name) {
		return CoreService.getInstance().doUpdateFunction("update_class", id, name);
	}

	public static HedspiObject[] getClassesList() {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_class_list");
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for(HashMap<String, Object>  it : rs){
			int id = (int)it.get("id");
			String name = (String)it.get("name");
			if (name == null)
				name = "";
			HedspiObject obj = new HedspiObject(id, name);
			ret.add(obj);
		}
		
		HedspiObject[] val = ret.toArray(new HedspiObject[ret.size()]);
		return val;
	}
}
