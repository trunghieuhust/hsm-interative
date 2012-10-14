package org.hsm.model;

import java.util.Properties;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.Lecturer;
import org.hsm.model.hedspiObject.Student;
import org.hsm.service.CityService;
import org.hsm.service.ClassService;
import org.hsm.service.CoreService;
import org.hsm.service.CourseService;
import org.hsm.service.DegreeService;
import org.hsm.service.DistrictService;
import org.hsm.service.FacultyService;
import org.hsm.service.LecturerService;
import org.hsm.service.RoomService;
import org.hsm.service.StudentService;

public class Model implements IModel {

	private static Model instance;

	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	private Model() {
	}

	@Override
	public Object getData(String command, Object... data) {
		switch (command) {
		case "saveCourse":
			return CourseService.save((int)data[0], (Course)data[1]);
		case "getCoursesList":
			return CourseService.getAll();
			
		case "newCourse":
			return CourseService.getNew();
			
		case "removeCourse":
			return CourseService.remove((int)data[0]);
			
		case "getFullDataCourse":
			return CourseService.getFull((int)data[0]);
			
		case "saveRoomName":
			return RoomService.rename((int)data[0], (String)data[1]);
			
		case "reloadRoomName":
			return RoomService.getReloadName((int)data[0]);
			
		case "getRoomList":
			return RoomService.getAll();
			
		case "newRoom":
			return RoomService.getNew();
			
		case "removeRoom":
			return RoomService.remove((int)data[0]);
			
		case "updateLecturer":
			return LecturerService.save((int)data[0], (Lecturer)data[1]);
			
		case "getListOfDegrees":
			return DegreeService.getAll();
			
		case "getFullDataLecturer":
			return LecturerService.getFullData((int)data[0]);
			
		case "getLecturersListInFaculty":
			return LecturerService.getLecturersInFaculty((int)data[0]);
			
		case "removeLecturer":
			return LecturerService.remove((int)data[0]);
			
		case "newLecturer":
			return LecturerService.getNewInFaculty((int)data[0]);
			
		case "renameFaculty":
			return FacultyService.rename((int)data[0], (String)data[1]);
			
		case "getListOfFaculties":
			return FacultyService.getFacutiesList();
			
		case "removeFaculty":
			return FacultyService.remove((int)data[0]);
			
		case "newFaculty":
			return FacultyService.getNew();
			
		case "saveCityName":
			return CityService.rename((int)data[0], (String)data[1]);
			
		case "getDistrictName":
			return DistrictService.getName((int)data[0]);
			
		case "saveDistrictName":
			return DistrictService.rename((int)data[0], (String)data[1]);
			
		case "removeDistrict":
			return DistrictService.remove((int)data[0]);
			
		case "newDistrict":
			return DistrictService.getNewInCity((int)data[0]);
			
		case "getCitiesList":
			return CityService.getCitiesList();
			
		case "removeCity":
			return CityService.remove((int)data[0]);
			
		case "newCity":
			return CityService.getNew();
			
		case "getCityOfDistrict":
			return DistrictService.getCityOfDistrict((int)data[0]);
			
		case "updateStudent":
			return StudentService.update((int)data[0], (Student)data[1]);
			
		case "getDistricsList":
			return DistrictService.getDistrictsList((int)data[0]);
			
		case "getCityList":
			return CityService.getCitiesList();
					
		case "getClassList":
			return ClassService.getClassesList();
			
		case "getFullStudentData":
			return StudentService.getFullDataStudent((int)data[0]);
			
		case "renameClass":
			return ClassService.rename((int)data[0], (String)data[1]);
			
		case "getNewRawStudentInClass":
			return StudentService.newRawInClass((int)data[0]);
			
		case "removeStudent":
			return StudentService.remove((int)data[0]);
					
		case "getStudentRawListInClass":
			return StudentService.getRawStudentListInClass((int)data[0]);
			
		case "removeClass":
			return ClassService.remove((int)data[0]);
			
		case "newClass":
			return ClassService.newClass();
		case "classList":
			return ClassService.getClassesList();
			
		case "check-login":
			Properties loginInfo = (Properties) data[0];
			return CoreService.isGoodLogin(loginInfo);

		default:
			Control.getInstance()
					.getLogger()
					.log(Level.WARNING,
							"Unsupported getData operation  - " + command
									+ ". Return null");
			return null;
		}
	}

	@Override
	public boolean setData(String command, Object... data) {
		switch (command) {
		default:
			Control.getInstance()
					.getLogger()
					.log(Level.WARNING,
							"Unsupported setData operation  - " + command);
			return false;
		}
	}

}
