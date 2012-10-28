package org.hsm.service;

import org.hsm.model.hedspiObject.HedspiObject;

public class DegreeService {

	public static HedspiObject[] getAll() {
		return CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction("get_degree_list"));
	}

}
