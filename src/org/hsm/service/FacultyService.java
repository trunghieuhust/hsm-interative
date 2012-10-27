package org.hsm.service;

import org.hsm.model.hedspiObject.HedspiObject;

public class FacultyService {

	public static HedspiObject getNew() {
		return CoreService.getInstance().firstSimpleResult(CoreService.getInstance().doQueryFunction("get_new_faculty"));
	}

	public static String remove(int i) {
		return CoreService.getInstance().doUpdateFunction("delete_faculty", i);
	}

	public static HedspiObject[] getFacutiesList() {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_faculty_list"));
	}

	public static String rename(int i, String string) {
		return CoreService.getInstance().doUpdateFunction("update_faculty", i, string);
	}

}
