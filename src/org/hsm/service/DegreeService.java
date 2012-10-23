package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DegreeService {

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().doQueryFunction("get_degree_list");
		for(HashMap<String, Object> it : rs)
			if (it.get("id") != null){
				int id = (int)it.get("id");
				String name = (String)it.get("name");
				ret.add(new HedspiObject(id, name));
			}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

}
