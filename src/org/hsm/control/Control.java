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
import org.hsm.model.hedspiObject.HedspiObject;
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

	public static Control getInstance() {
		if (instance == null)
			instance = new Control();
		return instance;
	}

	private Logger logger;
	private FileHandler logFileHandler;

	private MainWindow mainWindow;

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
			JOptionPane.showMessageDialog(null,
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
			JOptionPane.showMessageDialog(null,
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
			mainWindow = new MainWindow();//AllFunction();
			logger.log(Level.INFO, "Show main function window");
			mainWindow.setVisible(true);
		}
	}

	@Override
	public Object getData(String command, Object... data) {
		HedspiObject obj;
		Student student;
		int id;
		
		switch(command){
		case "getCityOfDistrict":
			id = (int) data[0];
			logger.log(Level.INFO, "Get id of city for district with id {" + id + "}");
			return Model.getInstance().getData("getCityOfDistrict", id);
			
		case "updateStudent":
			obj = (HedspiObject)data[0];
			student = (Student)data[1];
			return Model.getInstance().getData("updateStudent", obj.getId(), student);
			
		case "getDistricsList":
			id = (int)data[0];
			logger.log(Level.INFO, "Get list of districts in city having id {" + id + "}");
			return Model.getInstance().getData("getDistricsList", id);
			
		case "getCityList":
			logger.log(Level.INFO, "Get list of cities");
			return Model.getInstance().getData("getCityList");
			
		case "getClassList":
			logger.log(Level.INFO, "Get list of class");
			return Model.getInstance().getData("getClassList");
			
		case "getFullStudentData":
			obj = (HedspiObject)data[0];
			logger.log(Level.INFO, "Get full data of student {" + obj.getName() + "}");
			return Model.getInstance().getData("getFullStudentData", obj.getId());
		case "renameClass":
			obj = (HedspiObject)data[0];
			String name = (String)data[1];
			logger.log(Level.INFO, "Change class's name from {" + obj.getName() + "} to {" + name + "}");
			return Model.getInstance().getData("renameClass", obj.getId(), name);
		case "getNewRawStudentInClass":
			obj = (HedspiObject)data[0];
			logger.log(Level.INFO, "Insert new student in class {" + obj.getName() + "}");
			return Model.getInstance().getData("getNewRawStudentInClass", obj.getId());
			
		case "removeStudent":
			obj = (HedspiObject)data[0];
			logger.log(Level.INFO, "Remove student {" + obj.getName() + "}");
			return Model.getInstance().getData("removeStudent", obj.getId());
			
		case "getStudentRawListInClass":
			obj = (HedspiObject)data[0];
			logger.log(Level.INFO, "Get raw list of students in {" + obj.toString() +"} class");
			return Model.getInstance().getData("getStudentRawListInClass", obj.getId());
			
		case "removeClass":
			obj = (HedspiObject)data[0];
			logger.log(Level.INFO, String.format("Remove class with id = {%d}, name = {%s}", obj.getId(), obj.toString()));
			return Model.getInstance().getData("removeClass", obj.getId());
			
		case "newClass":
			logger.log(Level.INFO, "Create new class");
			return Model.getInstance().getData("newClass");
			
		case "classList":
			logger.log(Level.INFO, "Fetch class list");
			return Model.getInstance().getData("classList");
			
		default:
			logger.log(Level.WARNING,
					"A view has fired Control an operation that is not supported.\nCommand: "
							+ command);
			return null;
		}
	}
}
