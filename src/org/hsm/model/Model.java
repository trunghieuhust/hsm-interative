package org.hsm.model;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.AcademicInfo;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Lecturer;
import org.hsm.model.hedspiObject.Student;
import org.hsm.model.hedspiObject.Teach;
import org.hsm.service.CityService;
import org.hsm.service.ClassService;
import org.hsm.service.CoreService;
import org.hsm.service.CourseService;
import org.hsm.service.DegreeService;
import org.hsm.service.DistrictService;
import org.hsm.service.FacultyService;
import org.hsm.service.LecturerService;
import org.hsm.service.RestoreService;
import org.hsm.service.RoomService;
import org.hsm.service.StudentService;
import org.hsm.service.TeachService;
import org.hsm.service.UtilService;

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
		Object[] objs;

		switch (command) {
		case "executeQueryFunction":
			objs = new Object[data.length - 1];
			for (int i = 1; i < data.length; i++)
				objs[i - 1] = data[i];
			return CoreService.getInstance().doQueryFunction((String) data[0],
					objs);

		case "insertCourse":
			return CoreService.getInstance().doUpdateFunction("insert_course_",
					data[0], data[1], data[2], data[3], data[4], data[5],
					data[6]);
			// "insert_course_", nfees, ncredits,
			// topic, ctime, name, note, code);

		case "insertLecturer":
			return CoreService.getInstance().doUpdateFunction(
					"insert_lecturer", data[0], data[1], data[2], data[3],
					data[4], data[5], data[6], data[7], data[8], data[9],
					data[10]);
			// first, last, sex, dob, emails,
			// phones, note, home, dt, fc, dg

		case "insertStudent":
			return CoreService.getInstance().doUpdateFunction("insert_student",
					data[0], data[1], data[2], data[3], data[4], data[5],
					data[6], data[7], data[8], data[9], data[10], data[11],
					data[12], data[13]);
			// first, last, sex, dob, emails, phones, note, home, dt,
			// point, cl, mssv, year, k);

		case "restoreDatabase":
			return RestoreService.get_instance().client_copyin((File[]) data);

		case "getClassesListOfCourse":
			return CourseService.getClassesTeaching((int) data[0]);

		case "getClassesInRoom":
			return RoomService.getClassesHeldIn((int) data[0]);

		case "getLTeachedInfoOfLecturer":
			return LecturerService.getTeachedInfo((int) data[0]);

		case "getNameOfFactorsInTeachingClass":
			return TeachService.getFactorNames((int) data[0]);

		case "saveTeachInfo":
			return TeachService.save((Teach) data[0]);

		case "getListOfStudentsInTeachingClass":
			return TeachService.getStudentsList((int) data[0]);

		case "getFullDataOfTeachingClass":
			return TeachService.getFull((int) data[0]);

		case "removeTeachingClass":
			return TeachService.remove((int) data[0]);

		case "getNewTeachingClass":
			return TeachService.getNew();

		case "getRawListOfTeachingClasses":
			return TeachService.getRawList();

		case "getStatisticQueryResult":
			return UtilService.getResultOfStatisticQuery((String) data[0]);

		case "getLoginInfo":
			switch ((String) data[0]) {
			case "host":
				return CoreService.getInstance().getHost();
			case "port":
				return CoreService.getInstance().getPort();
			case "username":
				return CoreService.getInstance().getUsername();
			case "database":
				return CoreService.getInstance().getDatabase();
			}

		case "getSingleCourseStatistic":
			return CourseService.getSingleStatistic((int) data[0]);

		case "getCityOfDistrict":
			return DistrictService.getCity((int) data[0]);

		case "getSingleDistrictStatistic":
			return DistrictService.getSingleStatistic((int) data[0]);

		case "getSingleLecturerStatistic":
			return LecturerService.getSingleStatistic((int) data[0]);

		case "getStatisticOfStudent":
			return StudentService.getSingleStatistic((int) data[0]);

		case "query":
			return CoreService.getInstance().executeQuery((String) data[0]);

		case "getBackgroundRelated":
			return CourseService.getRelatedBackground((int) data[0]);

		case "saveAcademicInfo":
			return StudentService.saveAcademicInfo((int) data[0],
					(AcademicInfo[]) data[1]);

		case "getAcademicInfo":
			return StudentService.getAcademicInfo((int) data[0]);

		case "getLecturerListAll":
			return LecturerService.getAll();

		case "getBackgroundCourses":
			return CourseService.getBackgrounds((int) data[0]);

		case "getSuperFullStudents":
			return StudentService.getSuperFullStudents((int) data[0],
					(int) data[1]);

		case "getAddableBackgroundCourse":
			return CourseService.getAddableBackground((int) data[0]);

		case "saveCourse":
			return CourseService.save((int) data[0], (Course) data[1],
					(HedspiObject[]) data[2]);
		case "getCoursesList":
			return CourseService.getAll();

		case "newCourse":
			return CourseService.getNew();

		case "removeCourse":
			return CourseService.remove((int) data[0]);

		case "getFullDataCourse":
			return CourseService.getFull((int) data[0]);

		case "saveRoomName":
			return RoomService.rename((int) data[0], (String) data[1]);

		case "reloadRoomName":
			return RoomService.getReloadName((int) data[0]);

		case "getRoomList":
			return RoomService.getAll();

		case "newRoom":
			return RoomService.getNew();

		case "removeRoom":
			return RoomService.remove((int) data[0]);

		case "updateLecturer":
			return LecturerService.save((int) data[0], (Lecturer) data[1]);

		case "getListOfDegrees":
			return DegreeService.getAll();

		case "getFullDataLecturer":
			return LecturerService.getFullData((int) data[0]);

		case "getLecturersListInFaculty":
			return LecturerService.getLecturersInFaculty((int) data[0]);

		case "removeLecturer":
			return LecturerService.remove((int) data[0]);

		case "newLecturer":
			return LecturerService.getNewInFaculty((int) data[0]);

		case "renameFaculty":
			return FacultyService.rename((int) data[0], (String) data[1]);

		case "getListOfFaculties":
			return FacultyService.getFacutiesList();

		case "removeFaculty":
			return FacultyService.remove((int) data[0]);

		case "newFaculty":
			return FacultyService.getNew();

		case "saveCityName":
			return CityService.rename((int) data[0], (String) data[1]);

		case "getDistrictName":
			return DistrictService.getName((int) data[0]);

		case "saveDistrictName":
			return DistrictService.rename((int) data[0], (String) data[1]);

		case "removeDistrict":
			return DistrictService.remove((int) data[0]);

		case "newDistrict":
			return DistrictService.getNewInCity((int) data[0]);

		case "getCitiesList":
			return CityService.getCitiesList();

		case "removeCity":
			return CityService.remove((int) data[0]);

		case "newCity":
			return CityService.getNew();

		case "updateStudent":
			return StudentService.update((int) data[0], (Student) data[1]);

		case "getDistricsList":
			return DistrictService.getDistrictsList((int) data[0]);

		case "getCityList":
			return CityService.getCitiesList();

		case "getClassList":
			return ClassService.getClassesList();

		case "getFullStudentData":
			return StudentService.getFullDataStudent((int) data[0]);

		case "renameClass":
			return ClassService.rename((int) data[0], (String) data[1]);

		case "getNewRawStudentInClass":
			return StudentService.newRawInClass((int) data[0]);

		case "removeStudent":
			return StudentService.remove((int) data[0]);

		case "getStudentRawListInClass":
			return StudentService.getRawStudentListInClass((int) data[0]);

		case "removeClass":
			return ClassService.remove((int) data[0]);

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
