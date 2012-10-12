package org.hsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Student;

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

	public static Student getFullDataStudent(int id) {
		String query = "SELECT * FROM \"Student\" WHERE \"CT#\" = " + id;
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty())
			return null;
		HashMap<String, Object> ret = rs.get(0);
		
		int sid = (int)ret.get("CT#");
		
		String first = (String)ret.get("First");
		if (first == null)
			first = "";
		
		String last = (String)ret.get("Last");
		if (last == null)
			last = "";
		
		boolean isMale;
		if (ret.get("Sex") == null)
			isMale = true;
		else
			isMale = (boolean)ret.get("Sex");
		
		Date dob = (Date)ret.get("DOB");
		if (dob == null)
			dob = new Date();
		
		ArrayList<String> emails;
		String emailsStr = (String)ret.get("Emails");
		if (emailsStr == null)
			emailsStr = "";
		emails = endlineStringToArrayList(emailsStr);
		
		ArrayList<String> phones;
		String phonesStr = (String)ret.get("Phones");
		if (phonesStr == null)
			phonesStr = "";
		phones = endlineStringToArrayList(phonesStr);
		
		String note = (String)ret.get("Note");
		if (note == null)
			note = "";
		
		String home = (String)ret.get("Home");
		if (home == null)
			home = "";
		
		int district;
		if (ret.get("District") == null)
			district = -1;
		else
			district = (int)ret.get("District");
		
		double point;
		if (ret.get("Point") == null)
			point = 0;
		else
			point = (double)ret.get("Point");
		
		String mssv = (String)ret.get("MSSV");
		if (mssv == null)
			mssv = "";
		
		int year;
		if (ret.get("Year") == null)
			year = 0;
		else
			year = (int)ret.get("Year");
		
		int hedspiClass;
		if (ret.get("CL#")==null)
			hedspiClass = 0;
		else
			hedspiClass = (int)ret.get("CL#");
		
		return new Student(sid, first, last, isMale, dob, emails, phones, note, home, district, point, mssv, year, hedspiClass);
	}

	private static ArrayList<String> endlineStringToArrayList(String phonesStr) {
		ArrayList<String> ret = new ArrayList<>();
		for(String it : phonesStr.split("\n"))
			ret.add(it);
		return ret;
	}
	
	

}
