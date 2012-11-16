package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Teach;

public class TeachService {

	public static HedspiObject[] getRawList() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_raw_list_of_teaching_classes"));
	}

	public static HedspiObject getNew() {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction(
						"get_new_teaching_class"));
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction(
				"delete_teaching_class", i);
	}

	public static Teach getFull(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_full_teaching_class_info", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> ret = rs.get(0);

		String name = (String) ret.get("name");

		Object obj = ret.get("course");
		int course = (int) (obj == null ? 0 : obj);

		obj = ret.get("lecturer");
		int lecturer = (int) (obj == null ? 0 : obj);

		obj = ret.get("room");
		int room = (int) (obj == null ? 0 : obj);

		Teach teach = new Teach(i, name, course, lecturer, room);
		return teach;
	}

	public static String[] getStudentsList(int i) {
		ArrayList<String> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_students_list_in_teaching_class", i);
		for (HashMap<String, Object> it : rs) {
			String mssv = (String) it.get("mssv");
			String name = (String) it.get("name");
			String cl = (String) it.get("class_name");
			ret.add(mssv);
			ret.add(name);
			ret.add(cl);
		}
		return ret.toArray(new String[ret.size()]);
	}

	public static String save(Teach teach) {
		return CoreService.getInstance().doUpdateFunction(
				"save_teaching_class", teach.getId(), teach.getName(),
				teach.getCourse(), teach.getLecturer(), teach.getRoom());
	}

	public static String[] getFactorNames(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_factors_name_of_teaching_class", i);
		if (rs.isEmpty())
			return null;
		String[] ret = new String[3];
		HashMap<String, Object> tmp = rs.get(0);
		ret[0] = (String) tmp.get("course_name");
		ret[1] = (String) tmp.get("lecturer_name");
		ret[2] = (String) tmp.get("room_name");
		return ret;
	}
}
