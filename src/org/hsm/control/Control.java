/**
 * 
 */
package org.hsm.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import org.hsm.model.Model;
import org.hsm.model.hedspiObject.AcademicInfo;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Lecturer;
import org.hsm.model.hedspiObject.Student;
import org.hsm.service.CoreService;
import org.hsm.view.IView;
import org.hsm.view.login.LoginWindow;
import org.hsm.view.main.MainWindow;

/**
 * Thanh phan control trong mo hinh MVC. Control de dang single skeleton, goi
 * getInstance() de lay instance.
 * 
 * @author haidang-ubuntu
 * 
 */
public class Control implements IControl {
	private static final String LOG_DIR = "log";
	private static final String LOG_FILE_NAME = LOG_DIR
			+ "/"
			+ (new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar
					.getInstance().getTime())) + ".log";
	private static Control instance = null;
	private long time;
	private String currentStatus;
	private String queryMessage = "";

	public static Control getInstance() {
		if (instance == null)
			instance = new Control();
		return instance;
	}

	private Logger logger;
	private FileHandler logFileHandler;

	private MainWindow mainWindow = null;

	private IView login;

	/**
	 * init and open log
	 */
	private Control() {
		logger = Logger.getLogger("hedspi-student-manager");
		try {
			if (!(new File(LOG_DIR)).exists())
				(new File(LOG_DIR)).mkdir();
			logFileHandler = new FileHandler(LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"An error has occured while opening log file with name: "
							+ LOG_FILE_NAME + "\nMessage: " + e.getMessage(),
					"Opening log file failed", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		logFileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(logFileHandler);
	}

	/**
	 * triggered by any
	 */
	@Override
	public void fire(String command, Object... data) {
		switch (command) {
		case "start":
			start();
			break;
		case "exit":
			logger.log(Level.INFO, "System exits");
			System.exit(0);

		default:
			logger.log(Level.WARNING,
					"You have called Control an operation that is not supported.\nCommand: "
							+ command);
		}

	}

	/**
	 * Triggered by view
	 */
	@Override
	public void fireByView(IView view, String command, Object... data) {
		switch (command) {
		case "try-login":
			tryLogin(view, (Properties) data[0]);
			break;

		default:
			logger.log(Level.WARNING,
					"A view has fired Control an operation that is not supported.\nCommand: "
							+ command);
		}

	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	private void start() {
		try {
			logger.log(Level.INFO, "System starts");
			logger.log(Level.INFO, "Open login window");
			login = new LoginWindow();
			login.fire("set-visible", true);
		} catch (Throwable e) {
			logger.log(Level.SEVERE,
					"An error has occured when try to start program.\nMessage: "
							+ e.getMessage());
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"An error has occured when try to start program.\nMessage: "
							+ e.getMessage(), "Starting program errors",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void tryLogin(IView view, Properties loginInfo) {
		logger.log(Level.INFO, "Try login");
		boolean ok = (boolean) Model.getInstance().getData("check-login",
				loginInfo);
		if (!ok) {
			view.fire("login-fail");
			logger.log(Level.INFO, "Login failed");
		} else {
			// log
			logger.log(Level.INFO, "Login success");
			// save login info
			CoreService.getInstance().setLoginInfo(loginInfo);
			// hide current login
			view.fire("set-visible", false);
			// show function list
			mainWindow = new MainWindow();// AllFunction();
			logger.log(Level.INFO, "Show main function window");
			mainWindow.setVisible(true);
		}
	}

	/**
	 * Hàm chính của <code>Control</code>, thực hiện chức năng quản lý toàn phần
	 * mềm, các giao dịch dữ liệu,... Hàm thực hiện rất nhiều chức năng, các
	 * chức năng chủ yếu là ghi log, gọi {@link
	 * org.hsm.model.Model.getInstance()#getData(String, Object...)
	 * Model.getData} bằng cách chuyển trực tiếp câu lệnh cho
	 * <code>Model.getData</code>.
	 * <p>
	 * Lệnh huydt yêu cầu: lấy toàn bộ thông tin đầy đủ của sinh viên.
	 * <ul>
	 * <li>Lấy toàn bộ mang rất nhiều nghĩa, ở đây tôi chỉ lấy thông tin cơ bản.
	 * </li>
	 * <li>Gọi
	 * <code>(HashMap[])Control.getInstance().getData("getSuperFullStudents", offset, limit)</code>
	 * với <code>offset</code> là độ dịch vị, <code>limit</code> là giới hạn số
	 * lượng kết quả trả về</li>
	 * <li>Nếu độ dài mảng trả về nhỏ hơn limit tức là dữ liệu lấy đã đạt giới</li>
	 * <li>Thông tin mỗi sinh viên tương ứng một <code>HashMap</code></li>
	 * <li>Các trường dữ liệu được lấy về đảm bảo không <code>null</code> tương
	 * ứng với các kiểu dữ liệu thường dùng và có <code>key</code> là:
	 * <code>first, last, 
	 * district(String), city(String), class(String), phones(String[]), 
	 * emails(String[]),... </code></li>
	 * </ul>
	 * 
	 * @see org.hsm.control.IControl#getData(java.lang.String,
	 *      java.lang.Object[])
	 * @param command
	 *            lệnh truyền cho control.
	 * @param data
	 *            danh sách các tham số (optional)
	 */
	@Override
	public Object getData(String command, Object... data) {
		HedspiObject obj;
		Student student;
		int id;
		String name;
		Lecturer lecturer;
		Course course;
		int offset, limit;
		AcademicInfo[] academicInfos;
		String str;

		switch (command) {
		case "getSingleCourseStatistic":
			obj = (HedspiObject)data[0];
			logStart("Get statistic of single course {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getSingleCourseStatistic", obj.getId()));
			
		case "getNClassesInRoom":
			obj = (HedspiObject)data[0];
			logStart("Get number of classes held in room {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getNClassesInRoom", obj.getId()));
			
		case "getCityOfDistrictFromDistrictId":
			id = (int)data[0];
			logStart("Get city of district with id {" + id + "}");
			return logEnd(Model.getInstance().getData("getCityOfDistrict", id));
			
		case "getCityOfDistrict":
			obj = (HedspiObject)data[0];
			logStart("Get city of district {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getCityOfDistrict", obj.getId()));
			
		case "getSingleDistrictStatistic":
			obj = (HedspiObject)data[0];
			logStart("Get statistic information of district {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getSingleDistrictStatistic", obj.getId()));
			
		case "getSingleLecturerStatistic":
			obj = (HedspiObject) data[0];
			logStart("Get statistic information of lecturer {" + obj.getName()
					+ "}");
			return logEnd(Model.getInstance().getData(
					"getSingleLecturerStatistic", obj.getId()));

		case "getStatisticOfStudent":
			obj = (HedspiObject) data[0];
			logStart("Get statistic information of student {" + obj.getName()
					+ "}");
			return logEnd(Model.getInstance().getData("getStatisticOfStudent",
					obj.getId()));

		case "query":
			str = (String) data[0];
			logStart("Execute query {" + str + "}");
			return logEnd(Model.getInstance().getData("query", str));

		case "getBackgroundRelated":
			obj = (HedspiObject) data[0];
			logStart("Get background constrains related with course{"
					+ obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getBackgroundRelated",
					obj.getId()));

		case "saveAcademicInfo":
			obj = (HedspiObject) data[0];
			academicInfos = (AcademicInfo[]) data[1];
			logStart("Save academic information of student {" + obj.getName()
					+ "}");
			return logEnd(Model.getInstance().getData("saveAcademicInfo",
					obj.getId(), academicInfos));

		case "getAcademicInfo":
			obj = (HedspiObject) data[0];
			logStart("Get academic information of student {" + obj.getName()
					+ "}");
			return logEnd(Model.getInstance().getData("getAcademicInfo",
					obj.getId()));

		case "getLecturerListAll":
			logStart("Get list of all lecturers");
			return logEnd(Model.getInstance().getData("getLecturerListAll"));

		case "getBackgroundCourses":
			obj = (HedspiObject) data[0];
			logger.log(Level.INFO,
					"Get background courses of course {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getBackgroundCourses",
					obj.getId()));

		case "getSuperFullStudents":
			offset = (int) data[0];
			limit = (int) data[1];
			logStart("Get full data of students from " + offset + " to {1}"
					+ (offset + limit - 1));
			return logEnd(Model.getInstance().getData("getSuperFullStudents",
					offset, limit));

		case "getAddableBackgroundCourse":
			obj = (HedspiObject) data[0];
			logStart("Get addable background courses of course {"
					+ obj.getName() + "}");
			return logEnd(Model.getInstance().getData(
					"getAddableBackgroundCourse", obj.getId()));

		case "saveCourse":
			obj = (HedspiObject) data[0];
			course = (Course) data[1];
			logger.log(Level.INFO,
					"Save information of course {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("saveCourse",
					obj.getId(), course, data[2]));

		case "getCoursesList":
			logStart("Get courses list");
			return logEnd(Model.getInstance().getData("getCoursesList"));

		case "newCourse":
			logStart("Get new course");
			return logEnd(Model.getInstance().getData("newCourse"));

		case "removeCourse":
			obj = (HedspiObject) data[0];
			logStart("Remove course {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("removeCourse",
					obj.getId()));

		case "getFullDataCourse":
			obj = (HedspiObject) data[0];
			logStart("Get full data of course {" + obj.getName() + "}");
			return Model.getInstance()
					.getData("getFullDataCourse", obj.getId());

		case "saveRoomName":
			obj = (HedspiObject) data[0];
			name = (String) data[1];
			logStart("Change name of room {" + obj.getName() + "} into {"
					+ name + "}");
			return logEnd(Model.getInstance().getData("saveRoomName",
					obj.getId(), name));

		case "reloadRoomName":
			obj = (HedspiObject) data[0];
			logStart("Reload name of room {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("reloadRoomName",
					obj.getId()));

		case "getRoomList":
			logStart("Get list of rooms");
			return logEnd(Model.getInstance().getData("getRoomList"));

		case "newRoom":
			logStart("Get new room");
			return logEnd(Model.getInstance().getData("newRoom"));

		case "removeRoom":
			obj = (HedspiObject) data[0];
			logStart("Remove room {" + obj.getName() + "}");
			return logEnd(Model.getInstance()
					.getData("removeRoom", obj.getId()));

		case "updateLecturer":
			obj = (HedspiObject) data[0];
			lecturer = (Lecturer) data[1];
			logger.log(Level.INFO,
					"Save information of lecturer {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("updateLecturer",
					obj.getId(), lecturer));

		case "getListOfDegrees":
			logStart("Get list of degrees");
			return logEnd(Model.getInstance().getData("getListOfDegrees"));

		case "getFullDataLecturer":
			obj = (HedspiObject) data[0];
			logger.log(Level.INFO,
					"Get full data of lecturer {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getFullDataLecturer",
					obj.getId()));

		case "getLecturersListInFaculty":
			obj = (HedspiObject) data[0];
			logStart("Get lecturers in faculty {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData(
					"getLecturersListInFaculty", obj.getId()));

		case "removeLecturer":
			obj = (HedspiObject) data[0];
			logStart("Remove lecturer {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("removeLecturer",
					obj.getId()));

		case "newLecturer":
			obj = (HedspiObject) data[0];
			logStart("New lecturer in faculty {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("newLecturer",
					obj.getId()));

		case "renameFaculty":
			obj = (HedspiObject) data[0];
			name = (String) data[1];
			logStart("Rename faculty {" + obj.getName() + "} into {" + name
					+ "}");
			return logEnd(Model.getInstance().getData("renameFaculty",
					obj.getId(), name));

		case "getListOfFaculties":
			logStart("Get list of faculties");
			return logEnd(Model.getInstance().getData("getListOfFaculties"));

		case "removeFaculty":
			obj = (HedspiObject) data[0];
			logStart("Remove faculty {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("removeFaculty",
					obj.getId()));

		case "newFaculty":
			logStart("Create new faculty");
			return logEnd(Model.getInstance().getData("newFaculty"));

		case "saveCityName":
			obj = (HedspiObject) data[0];
			name = (String) data[1];
			logStart("Change name of city {" + obj.getName() + "} to {" + name
					+ "}");
			return logEnd(Model.getInstance().getData("saveCityName",
					obj.getId(), name));

		case "getDistrictName":
			obj = (HedspiObject) data[0];
			logStart("Reload name of district {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getDistrictName",
					obj.getId()));

		case "saveDistrictName":
			obj = (HedspiObject) data[0];
			name = (String) data[1];
			logStart("Change district name from {" + obj.getName() + "} into {"
					+ name + "}");
			return logEnd(Model.getInstance().getData("saveDistrictName",
					obj.getId(), name));

		case "getDistrictsListInCity":
			obj = (HedspiObject) data[0];
			logStart("Get new district in city {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getDistricsList",
					obj.getId()));

		case "removeDistrict":
			obj = (HedspiObject) data[0];
			logStart("Remove district {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("removeDistrict",
					obj.getId()));

		case "newDistrict":
			obj = (HedspiObject) data[0];
			logStart("Get new district of city {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("newDistrict",
					obj.getId()));

		case "getCitiesList":
			logStart("Get list of cities");
			return logEnd(Model.getInstance().getData("getCitiesList"));

		case "removeCity":
			obj = (HedspiObject) data[0];
			logStart("Remove city {" + obj.getName() + "}");
			return logEnd(Model.getInstance()
					.getData("removeCity", obj.getId()));

		case "newCity":
			logStart("Get new city");
			return logEnd(Model.getInstance().getData("newCity"));

		case "updateStudent":
			obj = (HedspiObject) data[0];
			student = (Student) data[1];
			logStart("Update information of student {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("updateStudent",
					obj.getId(), student));

		case "getDistricsList":
			id = (int) data[0];
			logStart("Get list of districts in city having id {" + id + "}");
			return logEnd(Model.getInstance().getData("getDistricsList", id));

		case "getCityList":
			logStart("Get list of cities");
			return logEnd(Model.getInstance().getData("getCityList"));

		case "getClassList":
			logStart("Get list of class");
			return logEnd(Model.getInstance().getData("getClassList"));

		case "getFullStudentData":
			obj = (HedspiObject) data[0];
			logStart("Get full data of student {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("getFullStudentData",
					obj.getId()));
		case "renameClass":
			obj = (HedspiObject) data[0];
			name = (String) data[1];
			logStart("Change class's name from {" + obj.getName() + "} to {"
					+ name + "}");
			return logEnd(Model.getInstance().getData("renameClass",
					obj.getId(), name));
		case "getNewRawStudentInClass":
			obj = (HedspiObject) data[0];
			logStart("Insert new student in class {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData(
					"getNewRawStudentInClass", obj.getId()));

		case "removeStudent":
			obj = (HedspiObject) data[0];
			logStart("Remove student {" + obj.getName() + "}");
			return logEnd(Model.getInstance().getData("removeStudent",
					obj.getId()));

		case "getStudentRawListInClass":
			obj = (HedspiObject) data[0];
			logStart("Get raw list of students in {" + obj.toString()
					+ "} class");
			return logEnd(Model.getInstance().getData(
					"getStudentRawListInClass", obj.getId()));

		case "removeClass":
			obj = (HedspiObject) data[0];
			logStart(String.format("Remove class with id = {%d}, name = {%s}",
					obj.getId(), obj.toString()));
			return logEnd(Model.getInstance().getData("removeClass",
					obj.getId()));

		case "newClass":
			logStart("Create new class");
			return logEnd(Model.getInstance().getData("newClass"));

		case "classList":
			logStart("Fetch class list");
			return logEnd(Model.getInstance().getData("classList"));

		default:
			logger.log(Level.WARNING,
					"A view has fired Control an operation that is not supported.\nCommand: "
							+ command);
			return null;
		}
	}

	private Object logEnd(Object data) {
		if (mainWindow != null)
			mainWindow.setStatus("(" + (System.currentTimeMillis() - time)
					+ " ms) " + currentStatus);
		return data;
	}

	private void logStart(String status) {
		logger.log(Level.INFO, status);
		if (mainWindow != null)
			mainWindow.setStatus(status + " ...");
		currentStatus = status;
		time = System.currentTimeMillis();
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	/**
	 * Trả về thông báo lỗi, sau đó reset biến nhớ về <code>""</code>
	 * @return thông báo lỗi hoặc <code>null</code> nếu ok.
	 * @see org.hsm.service.CoreService#query(String)
	 */
	public String getQueryMessage() {
		String queryMessage2 = queryMessage;
		setQueryMessage("");
		return queryMessage2;
	}

	public void setQueryMessage(String queryMessage) {
		this.queryMessage = queryMessage;
	}

}
