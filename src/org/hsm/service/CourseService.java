package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiObjectWithNote;

public class CourseService {

	public static Course getFull(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_course_data", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> it = rs.get(0);

		int id = (int) it.get("ce");
		String name = (String) it.get("name");
		double nFees = (double) it.get("nfees");
		int nCredits = (int) it.get("ncredits");

		String topic = (String) it.get("topic");
		if (topic == null)
			topic = "";

		double time = (double) it.get("ctime");

		String note = (String) it.get("note");
		if (note == null)
			note = "";

		String code = (String) it.get("code");
		if (code == null)
			code = "";

		Course course = new Course(id, name, nFees, nCredits, topic, time,
				note, code);
		return course;
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_course", i);
	}

	public static HedspiObject getNew() {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction("get_new_course"));
	}

	public static HedspiObject[] getAll() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_raw_courses_list"));
	}

	public static String save(int i, Course course, HedspiObject[] dependencies) {
		return CoreService.getInstance().doUpdateFunction("update_course",
				course.getName(), course.getNFees(), course.getNCredits(),
				course.getTopic(), course.getTime(), course.getNote(),
				course.getCode(), i, dependencies);
	}

	public static HedspiObject[] getAddableBackground(int i) {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_addable_background", i));
	}

	public static HedspiObject[] getBackgrounds(int i) {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_background_courses", i));
	}

	public static ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> getRelatedBackground(
			int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_related_backgrounds", i);
		ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> ret = new ArrayList<>();
		for (HashMap<String, Object> it : rs) {
			int rce = (int) it.get("rce");
			String rname = (String) it.get("rname");
			String rnote = (String) it.get("rnote");

			int mce = (int) it.get("mce");
			String mname = (String) it.get("mname");
			String mnote = (String) it.get("mnote");

			HedspiObjectWithNote r = new HedspiObjectWithNote(rce, rname, rnote);
			HedspiObjectWithNote m = new HedspiObjectWithNote(mce, mname, mnote);

			ret.add(new Pair<>(r, m));
		}

		return ret;
	}

	public static String[] getSingleStatistic(int i) {
		String[] ret = new String[8];
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_single_courses_statistic", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> tt = rs.get(0);
		ret[0] = String.valueOf((int) tt.get("teached_lecturers"));
		ret[1] = String.valueOf((int) tt.get("passed_students"));
		ret[2] = String.valueOf((int) tt.get("failed_students"));
		ret[3] = String.valueOf((double) tt.get("best_point"));
		ret[4] = String.valueOf((double) tt.get("worst_point"));
		ret[5] = String.valueOf((double) tt.get("average_point"));
		ret[6] = (String) tt.get("best_student");
		ret[7] = (String) tt.get("worst_student");
		return ret;
	}

	public static String[] getClassesTeaching(int i) {
		ArrayList<String> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_classes_teaching_course", i);
		for (HashMap<String, Object> it : rs) {
			ret.add((String) it.get("teach"));
			ret.add((String) it.get("lecturer"));
			ret.add((String) it.get("room"));
		}

		return ret.toArray(new String[ret.size()]);
	}

}
