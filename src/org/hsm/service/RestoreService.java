package org.hsm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.postgresql.copy.CopyManager;

public class RestoreService {
	private static final String[] PR = { "city", "district", "faculty",
			"cgroup", "class", "contact", "student", "degree", "lecturer",
			"room", "course", "background", "teach", "study" };

	CopyManager cpmanager;
	private Boolean done;
	private static RestoreService instance;

	public RestoreService() {
		try {
			cpmanager = CoreService.getInstance().getCopyManager();
		} catch (Exception e) {
			org.hsm.control.Control
					.getInstance()
					.getLogger()
					.log(Level.SEVERE,
							"Cannot get connection.\nMessage: "
									+ e.getMessage());
		}

	}

	/**
	 * 
	 * @return number of rows updated (for postgresql 8.1 or newer, -1 for
	 *         failed).
	 * @throws IOException
	 * 
	 */
	public long client_copyin(File[] list_file) {
		list_file = prSort(list_file);
		long number = 0;
		long total_rows = 0;
		String table;
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Restore start.");

		for (int i = 0; i < list_file.length; i++) {
			InputStreamReader fr = read_file_stream(list_file[i]);
			String statement = make_statement(list_file[i]);
			table = getTBName(list_file[i]);
			try {
				done = true;
				Control.getInstance().getLogger()
						.log(Level.INFO, "Start clearing old data.\n");
				String res = CoreService.getInstance().doUpdateFunction(
						"clear_for_restore", table);
				if (res != null) {
					done = false;
					return -1;
				}
				Control.getInstance()
						.getLogger()
						.log(Level.INFO,
								"Clearing done. Start copy in database table "
										+ table);
				number = cpmanager.copyIn(statement, fr);
			} catch (SQLException | IOException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.SEVERE,
								"On database usage errors or upon writer or database connection failure.\nMessage:\n "
										+ e.getMessage());
				done = false;
			}
			total_rows = total_rows + number;
			try {
				fr.close();
			} catch (IOException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.WARNING,
								"Restore database error. Message: "
										+ e.getMessage());
			}
		}
		if (done == true) {
			org.hsm.control.Control
					.getInstance()
					.getLogger()
					.log(Level.INFO,
							"Restore done.Total rows updated:" + total_rows);
		} else {
			org.hsm.control.Control.getInstance().getLogger()
					.log(Level.INFO, "Restore did not complete successfully\n");
			return -1;
		}

		return total_rows;
	}

	private File[] prSort(File[] list_file) {
		ArrayList<File> list = new ArrayList<>();
		for (File it : list_file)
			list.add(it);
		Collections.sort(list, new Comparator<File>() {

			@Override
			public int compare(File arg0, File arg1) {
				String tb0 = getTBName(arg0);
				String tb1 = getTBName(arg1);
				int id0 = findId(tb0);
				int id1 = findId(tb1);
				return id0 - id1;
			}

			private int findId(String tb1) {
				for (int i = 0; i < PR.length; i++)
					if (PR[i].equals(tb1))
						return i;
				return PR.length;
			}
		});
		return list.toArray(new File[list.size()]);
	}

	private String getTBName(File arg0) {
		return arg0.getName().substring(0, arg0.getName().lastIndexOf("."));
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

		String file_name = list_file.getName().substring(0,
				list_file.getName().lastIndexOf("."));
		String state = "COPY " + file_name + " FROM STDIN";
		return state;
	}

	public static RestoreService get_instance() {
		if (instance == null)
			instance = new RestoreService();
		return instance;
	}
}
