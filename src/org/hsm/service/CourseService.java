package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;

public class CourseService {

	public static Course getFull(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_course_data", i);
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
		return CoreService.getInstance().doUpdateFunction("delete_course", i);
	}

	public static HedspiObject getNew() {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_new_course");
		if (rs.isEmpty())
			return null;
		int id = (int)rs.get(0).get("id");
		String name = (String)rs.get(0).get("name");
		return new HedspiObject(id, name);
	}

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_raw_courses_list");
		for(HashMap<String, Object> it : rs){
			int id = (int)it.get("id");
			String name = (String)it.get("name");
			ret.add(new HedspiObject(id, name));
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String save(int i, Course course) {
		return CoreService.getInstance().doUpdateFunction("update_course",
				course.getName(),
				course.getNFees(),
				course.getNCredits(),
				course.getTopic(),
				course.getTime(),
				course.getNote(),
				course.getCode(),
				i);
	}

	public static HedspiObject[] getAddableBackground(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_addable_background", i);
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for(HashMap<String, Object> it : rs){
			String name = (String) it.get("name");
			int id = (int)it.get("id");
			ret.add(new HedspiObject(id, name));
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

}
