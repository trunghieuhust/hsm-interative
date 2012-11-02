package org.hsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.hsm.model.hedspiObject.AcademicInfo;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Student;

public class StudentService {

	public static HedspiObject[] getRawStudentListInClass(int classId) {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction(
						"get_raw_students_list_in_class", classId));
	}

	public static String remove(int id) {
		return CoreService.getInstance().doUpdateFunction(
				"delete_student_with_id", id);
	}

	public static HedspiObject newRawInClass(int id) {
		return CoreService.getInstance().firstSimpleResult(
				CoreService.getInstance().doQueryFunction(
						"insert_student_into_class", id));
	}

	public static Student getFullDataStudent(int id) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_full_data_student", id);
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
		emails = endlineStringToArray(emailsStr);

		String[] phones;
		String phonesStr = (String) ret.get("phones");
		if (phonesStr == null)
			phonesStr = "";
		phones = endlineStringToArray(phonesStr);

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

		double point;
		if (ret.get("point") == null)
			point = 0;
		else
			point = (double) ret.get("point");

		String mssv = (String) ret.get("mssv");
		if (mssv == null)
			mssv = "";

		int year;
		if (ret.get("year") == null)
			year = 0;
		else
			year = (int) ret.get("year");

		int hedspiClass;
		if (ret.get("cl") == null)
			hedspiClass = 0;
		else
			hedspiClass = (int) ret.get("cl");

		return new Student(sid, first, last, isMale, dob, emails, phones, note,
				home, district, point, mssv, year, hedspiClass);
	}

	public static String[] endlineStringToArray(String phonesStr) {
		return phonesStr.split("\n");
	}

	public static String arrayToEndlineString(String[] array) {
		String ret = "";
		for (String it : array)
			ret += it + "\n";
		return ret;
	}

	public static String update(int id, Student student) {
		return CoreService.getInstance().doUpdateFunction(
				"update_full_student", student.getFirst(), student.getLast(),
				student.isMale(), student.getDob().toString(),
				arrayToEndlineString(student.getEmails()),
				arrayToEndlineString(student.getPhones()), student.getNote(),
				student.getHome(), student.getDistrict(), student.getPoint(),
				student.getHedspiClass(), student.getMssv(), student.getYear(),
				id);
	}

	public static HashMap<String, Object>[] getSuperFullStudents(int offset,
			int limit) {
		String query = "SELECT \n" + "  student.first, \n"
				+ "  student.last, \n" + "  student.sex, \n"
				+ "  student.dob, \n" + "  student.emails, \n"
				+ "  student.phones, \n" + "  student.note, \n"
				+ "  student.home, \n" + "  student.point, \n"
				+ "  student.mssv, \n" + "  student.year, \n"
				+ "  district.name AS district, \n" + "  city.name AS city, \n"
				+ "  class.name AS class\n" + "FROM \n"
				+ "  public.student, \n" + "  public.district, \n"
				+ "  public.class, \n" + "  public.city\n" + "WHERE \n"
				+ "  student.ct = district.dt AND\n"
				+ "  student.cl = class.cl AND\n" + "  district.cy = city.cy\n"
				+ "ORDER BY \n" + "  student.last ASC" + "OFFSET " + offset
				+ "\n" + "LIMIT " + limit;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.query(query);
		for (HashMap<String, Object> it : rs) {
			for (Entry<String, Object> jt : it.entrySet()) {
				if (jt.getKey() == null)
					jt.setValue("");
			}
			it.put("emails", endlineStringToArray((String) it.get("emails")));
			it.put("phones", endlineStringToArray((String) it.get("phones")));
		}

		return rs.toArray(new HashMap[rs.size()]);
	}

	public static AcademicInfo[] getAcademicInfo(int i) {
		ArrayList<AcademicInfo> all = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_academic_info", i);
		for (HashMap<String, Object> it : rs) {
			int ce = (int) it.get("ceid");
			String ce_name = (String) it.get("ce_name");
			HedspiObject course = new HedspiObject(ce, ce_name);

			int lt = (int) it.get("lt");
			String lt_name = (String) it.get("lt_name");
			HedspiObject lecturer = new HedspiObject(lt, lt_name);

			int rm = (int) it.get("rmid");
			String rm_name = (String) it.get("rm_name");
			HedspiObject room = new HedspiObject(rm, rm_name);

			boolean isPassed = (boolean) it.get("is_passed");
			double result = (double) it.get("ret");

			all.add(new AcademicInfo(course, lecturer, room, isPassed, result));
		}
		return all.toArray(new AcademicInfo[all.size()]);
	}

	public static String saveAcademicInfo(int i, AcademicInfo[] academicInfos) {
		double[] arr = new double[academicInfos.length * 5];
		for (int it = 0, j = 0; it < academicInfos.length; it++) {
			arr[j++] = academicInfos[it].getCourse().getId();
			arr[j++] = academicInfos[it].getLecturer().getId();
			arr[j++] = academicInfos[it].getRoom().getId();
			arr[j++] = academicInfos[it].isPassed() ? 1 : 0;
			arr[j++] = academicInfos[it].getResult();
		}
		return CoreService.getInstance().doUpdateFunction(
				"update_academic_info", i, arr);
	}

	public static Number[] getSingleStatistic(int i) {
		Number[] result = new Number[8];
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_single_student_statistic8", i);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> tt = rs.get(0);
		result[0] = new Integer((int) tt.get("passed_classes"));
		result[1] = new Integer((int) tt.get("failed_classes"));
		result[2] = new Integer((int) tt.get("passed_courses"));
		result[3] = new Integer((int) tt.get("failed_courses"));
		result[4] = new Double((double) tt.get("max_point"));
		result[5] = new Double((double) tt.get("min_point"));
		result[6] = new Double((double) tt.get("avg_point"));
		result[7] = new Double((double) tt.get("avg_max_point"));
		return result;
	}
}