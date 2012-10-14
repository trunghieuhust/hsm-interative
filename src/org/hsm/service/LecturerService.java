package org.hsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Lecturer;

public class LecturerService {

	public static HedspiObject getNewInFaculty(int i) {
		String query  = "INSERT INTO \"Lecturer\" (\"FC#\") VALUES (" + i + ")\n" +
				"RETURNING \"CT#\", \"First\", \"Last\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		if (rs.isEmpty() || rs.get(0).get("CT#") == null)
			return null;
		int id = (int)rs.get(0).get("CT#");
		String first= (String)rs.get(0).get("First");
		String last = (String)rs.get(0).get("Last");
		String name = first  + " " + last;
		return new HedspiObject(id, name);
	}

	public static String remove(int i) {
		String query = "DELETE FROM \"Lecturer\" WHERE \"CT#\" = " + i;
		return CoreService.getInstance().update(query);
	}

	public static HedspiObject[] getLecturersInFaculty(int i) {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"CT#\", \"First\", \"Last\"\n" +
				"FROM \"Lecturer\"\n" +
				"WHERE \"FC#\" = " + i + "\n" +
						"ORDER BY \"Last\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs)
			if (it.get("CT#") != null){
				int id = (int)it.get("CT#");
				String first= (String)it.get("First");
				String last = (String)it.get("Last");
				String name = first + " " + last;
				ret.add(new HedspiObject(id, name));
			}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	public static Lecturer getFullData(int id) {
		String query = "SELECT * FROM \"Lecturer\" WHERE \"CT#\" = " + id;
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
		
		String[] emails;
		String emailsStr = (String)ret.get("Emails");
		if (emailsStr == null)
			emailsStr = "";
		emails = StudentService.endlineStringToArray(emailsStr);
		
		String[] phones;
		String phonesStr = (String)ret.get("Phones");
		if (phonesStr == null)
			phonesStr = "";
		phones = StudentService.endlineStringToArray(phonesStr);
		
		String note = (String)ret.get("Note");
		if (note == null)
			note = "";
		
		String home = (String)ret.get("Home");
		if (home == null)
			home = "";
		
		int district;
		if (ret.get("DT#") == null)
			district = -1;
		else
			district = (int)ret.get("DT#");
		
		int degree;
		if (ret.get("DG#") == null)
			degree = -1;
		else
			degree = (int)ret.get("DG#");
		
		int faculty;
		if (ret.get("FC#") == null)
			faculty = -1;
		else
			faculty = (int)ret.get("FC#");
		
		Lecturer lecturer = new Lecturer(sid, first, last, isMale, dob, emails, phones, note, home, district, degree, faculty);
		return lecturer;
	}

	public static String save(int id, Lecturer lecturer) {
		String query = String.format("UPDATE \"Lecturer\"\n" +
				"SET\n" +
				"\"First\" = '%s'\n" +
				", \"Last\" = '%s'\n" +
				", \"Sex\" = %s\n" +
				", \"DOB\" = '%s'\n" +
				", \"Emails\" = '%s'\n" +
				", \"Phones\" = '%s'\n" +
				", \"Note\" = '%s'\n" +
				", \"Home\" = '%s'\n" +
				", \"DT#\" = %d\n" +
				", \"FC#\" = %d\n" +
				", \"DG#\" = %d\n" +
				"WHERE \"CT#\" = %d", 
				lecturer.getFirst().replace("'", "''"),
				lecturer.getLast().replace("'", "''"),
				lecturer.isMale() ? "true" : "false",
				lecturer.getDob().toString(),
				StudentService.arrayToEndlineString(lecturer.getEmails()).replace("'", "''"),
				StudentService.arrayToEndlineString(lecturer.getPhones()).replace("'", "''"),
				lecturer.getNote().replace("'", "''"),
				lecturer.getHome().replace("'", "''"),
				lecturer.getDistrict(),
				lecturer.getFaculty(),
				lecturer.getDegree(),
				id);

		return CoreService.getInstance().update(query);
	}

}
