package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;

public class CourseService {

	public static Course getFull(int i) {
		String query = "SELECT * FROM \"Course\" WHERE \"CE#\" = " + i;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> it = rs.get(0);
		
		int id = (int)it.get("CE#");
		String name = (String)it.get("Name");
		double nFees = (double)it.get("NFees");
		int nCredits = (int)it.get("NCredits");
		
		String topic = (String)it.get("Topic");
		if (topic == null)
			topic = "";
		
		double time = (double)it.get("Time");
		
		String note = (String)it.get("Note");
		if (note == null)
			note = "";
		
		String code = (String)it.get("Code");
		if (code == null)
			code = "";
		
		Course course = new Course(id, name, nFees, nCredits, topic, time, note, code);
		return course;
	}

	public static String remove(int i) {
		String query = "DELETE FROM \"Course\" WHERE \"CE#\" = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject getNew() {
		String query = "INSERT INTO \"Course\" DEFAULT VALUES RETURNING \"CE#\", \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty())
			return null;
		int id = (int)rs.get(0).get("CE#");
		String name = (String)rs.get(0).get("Name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"CE#\", \"Name\" FROM \"Course\" ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("CE#");
			String name = (String)it.get("Name");
			ret.add(new HedspiObject(id, name));
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String save(int i, Course course) {
		String query = String.format("UPDATE \"Course\"\n" +
				"SET\n" +
				"\"Name\" = '%s'\n" +
				", \"NFees\" = '%f'\n" +
				", \"NCredits\" = %d\n" +
				", \"Topic\" = '%s'\n" +
				", \"Time\" = '%f'\n" +
				", \"Note\" = '%s'\n" +
				", \"Code\" = '%s'\n" +
				"WHERE \"CE#\" = %d", 
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
