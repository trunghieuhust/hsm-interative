package org.hsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Student;

public class StudentService {

	public static HedspiObject[] getRawStudentListInClass(int classId) {
		ArrayList<HedspiObject> ret = new ArrayList<HedspiObject>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("get_raw_students_list_in_class", classId);
		for (HashMap<String, Object> it : rs) {
			int id = (int) it.get("id");
			String name = (String) it.get("name");
			HedspiObject st = new HedspiObject(id, name);
			ret.add(st);
		}

		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static String remove(int id) {
		return CoreService.getInstance().doUpdateFunction(
				"delete_student_with_id", id);
	}

	public static HedspiObject newRawInClass(int id) {
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance()
				.doQueryFunction("insert_student_into_class", id);
		HashMap<String, Object> it;
		if (rs.size() > 0) {
			it = rs.get(0);
			int stid = (int) it.get("id");
			String name = (String) it.get("name");
			HedspiObject st = new HedspiObject(stid, name);
			return st;
		}

		return null;
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
}
