package org.hsm.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.hsm.model.hedspiObject.HedspiObject;

public class DegreeService {

	public static HedspiObject[] getAll() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		String query = "SELECT \"DG#\", \"Name\" FROM \"Degree\" ORDER BY \"Name\"";
		ArrayList<HashMap<String, Object>> rs = CoreService.getInstance().query(query);
		for(HashMap<String, Object> it : rs)
			if (it.get("DG#") != null){
				int id = (int)it.get("DG#");
				String name = (String)it.get("Name");
				ret.add(new HedspiObject(id, name));
			}
		
		return ret.toArray(new HedspiObject[ret.size()]);
	}

}
