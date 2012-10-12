package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiClass;

public class ClassService {

	public static HedspiClass[] getClasses() {
		ArrayList<HedspiClass> classes = new ArrayList<>();
		String query = "SELECT \"CL#\", \"Name\" FROM \"Class\"\n" +
				"ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		for (HashMap<String, Object> it : rs) {
			int cl = (int) it.get("CL#");
			String name = (String) it.get("Name");
			if (name == null)
				name = "";
			HedspiClass cla = new HedspiClass(cl, name);
			classes.add(cla);
		}
		return classes.toArray(new HedspiClass[classes.size()]);
	}

	public static HedspiClass newClass() {
		String query = "INSERT INTO \"Class\" DEFAULT VALUES\n" +
				" RETURNING \"CL#\", \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		int cl = (int)rs.get(0).get("CL#");
		String name = (String)rs.get(0).get("Name");
		if (name == null)
			name = "";
		HedspiClass cla = new HedspiClass(cl, name);
		return cla;
	}

	public static String removeClass(int cl){
		String query = "DELETE FROM \"Class\" WHERE \"CL#\" = " + cl;
		return CoreService.getInstance().update(query);
	}

	public static String rename(int id, String name) {
		String query = "UPDATE \"Class\"\n" +
				" SET \"Name\" = '" + name.replace("'", "''") + "'\n" +
				"WHERE \"CL#\" = " + id;
		return CoreService.getInstance().update(query);
	}
}
