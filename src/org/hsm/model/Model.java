package org.hsm.model;

import java.util.Properties;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.hsm.service.ClassService;
import org.hsm.service.CoreService;
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
		case "getNewRawStudentInClass":
			return StudentService.newRawStudentInClass((int)data[0]);
			
		case "removeStudent":
			return StudentService.removeStudent((int)data[0]);
					
		case "getStudentRawListInClass":
			return StudentService.getRawStudentListInClass((int)data[0]);
			
		case "removeClass":
			return ClassService.removeClass((int)data[0]);
			
		case "newClass":
			return ClassService.newClass();
		case "classList":
			return ClassService.getClasses();
			
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
