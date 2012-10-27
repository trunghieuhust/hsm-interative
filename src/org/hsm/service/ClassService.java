package org.hsm.service;

import org.hsm.model.hedspiObject.HedspiObject;

public class ClassService {

	public static HedspiObject newClass() {
		return CoreService.getInstance().firstSimpleResult(CoreService.getInstance()
				.doQueryFunction("get_new_class"));
	}

	public static String remove(int cl){
		return CoreService.getInstance().doUpdateFunction("delete_class", cl);
	}

	public static String rename(int id, String name) {
		return CoreService.getInstance().doUpdateFunction("update_class", id, name);
	}

	public static HedspiObject[] getClassesList() {
		return CoreService.getInstance().rsToSimpleArray(CoreService.getInstance().doQueryFunction("get_class_list"));
	}
}
