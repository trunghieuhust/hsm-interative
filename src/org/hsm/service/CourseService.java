package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;

public class CourseService {

	public static Course getFull(int i) {
		String query = "SELECT * FROM course WHERE ce = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> it = rs.get(0);
		
		int id = (int)it.get("ce");
		String name = (String)it.get("name");
		double nFees = (double)it.get("nfees");
		int nCredits = (int)it.get("ncredits");
		
		String topic = (String)it.get("topic");
		if (topic == null)
			topic = "";
		
		double time = (double)it.get("time");
		
		String note = (String)it.get("note");
		if (note == null)
			note = "";
		
		String code = (String)it.get("code");
		if (code == null)
			code = "";
		
		Course course = new Course(id, name, nFees, nCredits, topic, time, note, code);
		return course;
	}

	public static String remove(int i) {
		String query = "DELETE FROM course WHERE ce = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO course DEFAULT VALUES RETURNING ce, name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty())
			return null;
		int id = (int)rs.get(0).get("ce");
		String name = (String)rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT ce, name FROM course ORDER BY name";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("ce");
			String name = (String)it.get("name");
			ret.add(new HedspiObject(id, name));
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String save(int i, Course course) {
		String query = String.format("UPDATE course\n" +
				"SET\n" +
				"name = '%s'\n" +
				", nfees = '%f'\n" +
				", ncredits = %d\n" +
				", topic = '%s'\n" +
				", time = '%f'\n" +
				", note = '%s'\n" +
				", code = '%s'\n" +
				"WHERE ce = %d", 
				course.getName().replace("'", "''"),
				course.getNFees(),
				course.getNCredits(),
				course.getTopic().replace("'", "''"),
				course.getTime(),
				course.getNote().replace("'", "''"),
				course.getCode().replace("'", "''"),
				i);
		return CoreService.getInstance().update(query);
	}

}
