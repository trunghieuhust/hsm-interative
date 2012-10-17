package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class ClassService {

	public static HedspiObject newClass() {
		String query = "INSERT INTO Class DEFAULT VALUES\n" +
				" RETURNING CL, Name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		int cl = (int)rs.get(0).get("CL");
		String name = (String)rs.get(0).get("Name");
		HedspiObject cla = new HedspiObject(cl, name);
		return cla;
	}

	public static String remove(int cl){
		String query = "DELETE FROM class WHERE cl = " + cl;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int id, String name) {
		String query = "UPDATE class\n" +
				" SET name = '" + name.replace("'", "''") + "'\n" +
				"WHERE cl = " + id;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject[] getClassesList() {
		String query = "SELECT cl, name FROM class\n" +
				"ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for(HashMap<String, Object>  it : rs){
			int id = (int)it.get("cl");
			String name = (String)it.get("name");
			if (name == null)
				name = "";
			HedspiObject obj = new HedspiObject(id, name);
			ret.add(obj);
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}
}
