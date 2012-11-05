package org.hsm.service;

public class UtilService {

	public static String[] getResultOfStatisticQuery(String string) {
		Object[] rs = CoreService.getInstance().executeQuery(string);
		String message = (String) rs[0];
		int n = (int) rs[1];
		if (n == 0)
			return new String[] { message, null };

		if (rs.length <= n + 2)
			return new String[] { "empty result", null };

		String result = rs[n + 2] == null ? "null" : rs[n + 2].toString();
		return new String[] { null, result };
	}

}
