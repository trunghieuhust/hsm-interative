package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class ClassService {

	public static HedspiObject newClass() {
		String query = "INSERT INTO \"Class\" DEFAULT VALUES\n" +
				" RETURNING \"CL#\", \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		int cl = (int)rs.get(0).get("CL#");
		String name = (String)rs.get(0).get("Name");
		HedspiObject cla = new HedspiObject(cl, name);
		return cla;
	}

	public static String remove(int cl){
		String query = "DELETE FROM \"Class\" WHERE \"CL#\" = " + cl;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int id, String name) {
		String query = "UPDATE \"Class\"\n" +
				" SET \"Name\" = '" + name.replace("'", "''") + "'\n" +
				"WHERE \"CL#\" = " + id;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject[] getClassesList() {
		String query = "SELECT \"CL#\", \"Name\" FROM \"Class\"\n" +
				"ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for(HashMap<String, Object>  it : rs){
			int id = (int)it.get("CL#");
			String name = (String)it.get("Name");
			if (name == null)
				name = "";
			HedspiObject obj = new HedspiObject(id, name);
			ret.add(obj);
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}
}
