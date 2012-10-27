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
		
		double time = (double)it.get("ctime");
		
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
		return CoreService.getInstance().firstSimpleResult(CoreService.getInstance().doQueryFunction("get_new_course"));
	}

	public static HedspiObject[] getAll() {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_raw_courses_list"));
	}

	public static String save(int i, Course course, HedspiObject[] dependencies) {
		return CoreService.getInstance().doUpdateFunction("update_course",
				course.getName(),
				course.getNFees(),
				course.getNCredits(),
				course.getTopic(),
				course.getTime(),
				course.getNote(),
				course.getCode(),
				i,
				dependencies);
	}

	public static HedspiObject[] getAddableBackground(int i) {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_addable_background", i));
	}

	public static HedspiObject[] getBackgrounds(int i) {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_background_courses", i));
	}

}
