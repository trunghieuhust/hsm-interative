package org.hsm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.postgresql.copy.CopyManager;

public class RestoreService {
	CopyManager cpmanager;
	private static RestoreService instance;

	public RestoreService() {
		try {
			cpmanager = CoreService.getInstance().getCopyManager();
		} catch (Exception e) {
			System.out.println(e.toString());
			org.hsm.control.Control.getInstance().getLogger()
					.log(Level.SEVERE, "Cannot get connection.\n");
		}

	}

	/**
	 * 
	 * @return number of rows updated (for postgresql 8.1 or newer, -1 for
	 *         older).
	 * @throws IOException
	 * 
	 */
	public long client_copyin(File[] list_file) throws IOException {
		long number = 0;
		long total_rows = 0;

		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Restore start.");

		for (int i = 0; i < list_file.length; i++) {
			InputStreamReader fr = read_file_stream(list_file[i]);
			String statement = make_statement(list_file[i]);
			try {
				number = cpmanager.copyIn(statement, fr);
			} catch (SQLException | IOException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.SEVERE,
								"On database usage errors or upon writer or database connection failure ");
				e.printStackTrace();
			}
			total_rows = total_rows + number;
			fr.close();
		}
		org.hsm.control.Control
				.getInstance()
				.getLogger()
				.log(Level.INFO,
						"Restore done.Total rows updated:" + total_rows);
		return total_rows;
	}

	private InputStreamReader read_file_stream(File list_file) {
		InputStream is = null;
		InputStreamReader isr = null;
		try {
			is = new FileInputStream(list_file);
			isr = new InputStreamReader(is, "UTF-8");
		} catch (Exception e) {
			org.hsm.control.Control
					.getInstance()
					.getLogger()
					.log(Level.SEVERE, "Cannot read file" + list_file.getName());
		}
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Read from file " + list_file.getName());
		return isr;
	}

	private String make_statement(File list_file) {
		return "COPY " + list_file.getName() + " FROM STDIN";
	}

	public static RestoreService get_instance() {
		if (instance == null)
			instance = new RestoreService();
		return instance;
	}
}
