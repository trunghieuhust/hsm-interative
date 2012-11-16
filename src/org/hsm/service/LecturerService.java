package org.hsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Lecturer;

public class LecturerService {

	public static HedspiObject getNewInFaculty(int i) {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction(
						"get_new_lecturer_in_faculty", i));
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_lecturer", i);
	}

	public static HedspiObject[] getLecturersInFaculty(int i) {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_raw_lecturers_list_in_faculty", i));
	}

	public static Lecturer getFullData(int id) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_full_data_lecturer_in_faculty", id);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> ret = rs.get(0);

		int sid = (int) ret.get("ct");

		String first = (String) ret.get("first");
		if (first == null)
			first = "";

		String last = (String) ret.get("last");
		if (last == null)
			last = "";

		boolean isMale;
		if (ret.get("sex") == null)
			isMale = true;
		else
			isMale = (boolean) ret.get("sex");

		Date dob = (Date) ret.get("dob");
		if (dob == null)
			dob = new Date();

		String[] emails;
		String emailsStr = (String) ret.get("emails");
		if (emailsStr == null)
			emailsStr = "";
		emails = StudentService.endlineStringToArray(emailsStr);

		String[] phones;
		String phonesStr = (String) ret.get("phones");
		if (phonesStr == null)
			phonesStr = "";
		phones = StudentService.endlineStringToArray(phonesStr);

		String note = (String) ret.get("note");
		if (note == null)
			note = "";

		String home = (String) ret.get("home");
		if (home == null)
			home = "";

		int district;
		if (ret.get("dt") == null)
			district = -1;
		else
			district = (int) ret.get("dt");

		int degree;
		if (ret.get("dg") == null)
			degree = -1;
		else
			degree = (int) ret.get("dg");

		int faculty;
		if (ret.get("fc") == null)
			faculty = -1;
		else
			faculty = (int) ret.get("fc");

		Lecturer lecturer = new Lecturer(sid, first, last, isMale, dob, emails,
				phones, note, home, district, degree, faculty);
		return lecturer;
	}

	public static String save(int id, Lecturer lecturer) {
		return CoreService.getInstance().doUpdateFunction("update_lecturer",
				lecturer.getFirst(), lecturer.getLast(), lecturer.isMale(),
				lecturer.getDob().toString(),
				StudentService.arrayToEndlineString(lecturer.getEmails()),
				StudentService.arrayToEndlineString(lecturer.getPhones()),
				lecturer.getNote(), lecturer.getHome(), lecturer.getDistrict(),
				lecturer.getFaculty(), lecturer.getDegree(), id);
	}

	public static HedspiObject[] getAll() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction("get_all_lecturers"));
	}

	public static String[] getSingleStatistic(int i) {
		String[] ret = new String[5];
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_single_lecturer_statistic5", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> tt = rs.get(0);
		ret[0] = String.valueOf((int) tt.get("teached_classes"));
		ret[1] = String.valueOf((int) tt.get("teached_courses"));
		ret[2] = String.valueOf((int) tt.get("teached_students"));
		ret[3] = (String) tt.get("best_student");
		ret[4] = (String) tt.get("worst_student");
		return ret;
	}

	public static String[] getTeachedInfo(int i) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_teach_info_lecturer", i);
		ArrayList<String> ret = new ArrayList<>();
		for (HashMap<String, Object> it : rs) {
			ret.add((String) it.get("teach"));
			ret.add((String) it.get("course"));
			ret.add((String) it.get("room"));
		}
		return ret.toArray(new String[ret.size()]);
	}
}
