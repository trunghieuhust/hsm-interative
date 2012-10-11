package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class StudentService {

	public static HedspiObject[] getRawStudentListInClass(int classId) {
		ArrayList<HedspiObject> ret = new ArrayList<HedspiObject>();
		String query = "SELECT \"CT#\", \"First\", \"Last\" FROM \"Student\"\n" +
				" WHERE \"Student\".\"CL#\" = " + classId + "\n" +
				" ORDER BY \"Last\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object>  it : rs){
			int id = (int)it.get("CT#");
			String fname = (String)it.get("First");
			String lname = (String)it.get("Last");
			String name = fname + " " + lname;
			HedspiObject st = new HedspiObject(id, name);
			ret.add(st);
		}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String removeStudent(int id) {
		String query = "DELETE FROM \"Student\" WHERE \"CT#\" = " + id;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject newRawStudentInClass(int id) {
		String query = "INSERT INTO \"Student\" (\"CL#\") VALUES ( " + id + ")\n" +
				" RETURNING \"First\", \"Last\", \"CT#\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		HashMap<String, Object> it;
		if (rs.size() > 0){
			it = rs.get(0);
			int stid = (int)it.get("CT#");
			String fname = (String)it.get("First");
			String lname = (String)it.get("Last");
			String name = fname + " " + lname;
			HedspiObject st = new HedspiObject(stid, name);
			return st;
		}
		
		return null;
	}
	
	

}
