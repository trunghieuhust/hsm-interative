package org.hsm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;

import org.hsm.control.Control;
import org.hsm.view.misc.SelectTablePane;
import org.postgresql.copy.CopyManager;

public class BackupService {
	CopyManager cpmanager;
	private static BackupService instance;

	public BackupService() {
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
	 * @return number of row (for postgresql 8.1 or newer, -1 for older).
	 * @throws IOException
	 * 
	 */
	public long client_copyout(String url) throws IOException {
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Server backup start.");
		String folder_name = make_backup_folder(url);
		long number = 0;
		long total_rows = 0;
		for (int i = 0; i < SelectTablePane.table_name.length; i++) {
			if (SelectTablePane.isbackup[i] == 1) {
				OutputStream fos = make_file_stream(
						SelectTablePane.table_name[i], folder_name);
				String statement = make_statement(SelectTablePane.table_name[i]);
				try {
					number = cpmanager.copyOut(statement, fos);
				} catch (SQLException | IOException e) {
					Control.getInstance()
							.getLogger()
							.log(Level.SEVERE,
									"On database usage errors or upon writer or database connection failure ");
					e.printStackTrace();
				}
				total_rows = total_rows + number;
				fos.close();
			}
		}
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Backup done.Backup folder:" + folder_name);
		return total_rows;
	}

	private String make_backup_folder(String url) throws IOException {
		String folder_name;
		if (url == null)
			folder_name = "backup_"
					+ CoreService
							.getInstance()
							.firstSimpleResult(
									CoreService.getInstance().doQueryFunction(
											"get_current_time")).getName();
		else {
			folder_name = url.substring(0, url.length())
					+ "/"
					+ "backup_"
					+ CoreService
							.getInstance()
							.firstSimpleResult(
									CoreService.getInstance().doQueryFunction(
											"get_current_time")).getName();
		}
		try {
			File backup_dir = new File(folder_name);
			backup_dir.mkdir();
		} catch (Exception e) {
			org.hsm.control.Control.getInstance().getLogger()
					.log(Level.SEVERE, "Cannot make folder" + folder_name);
		}
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Make folder " + folder_name);

		return folder_name;
	}

	private OutputStream make_file_stream(String file_name, String folder) {
		String name;
		OutputStream fos = null;
		name = folder + "/" + file_name + ".backup";

		try {
			fos = new FileOutputStream(name, true);
		} catch (Exception e) {
			org.hsm.control.Control.getInstance().getLogger()
					.log(Level.SEVERE, "Cannot make file");
		}
		org.hsm.control.Control.getInstance().getLogger()
				.log(Level.INFO, "Make file " + file_name + ".backup");

		return fos;
	}

	private String make_statement(String table_name) {
		return "COPY " + table_name + " TO STDOUT";
	}

	public static BackupService get_instance() {
		if (instance == null)
			instance = new BackupService();
		return instance;
	}
}
