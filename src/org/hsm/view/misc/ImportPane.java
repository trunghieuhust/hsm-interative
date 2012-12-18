package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hsm.control.Control;

import au.com.bytecode.opencsv.CSVReader;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ImportPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField CSVpath;
	private String path;

	/**
	 * Create the panel.
	 */
	public String get_csv_file() {
		File list_file;
		list_file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose backup file");
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
			return null;
		list_file = fileChooser.getSelectedFile();
		return list_file.getPath();
	}

	public ImportPane() {

		String[] importObject = { "Students", "Lecturers", "Courses" };
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.UNRELATED_GAP_COLSPEC,
						ColumnSpec.decode("94px"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("96px:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("24px"),
						FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("25px"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("Import object");
		add(lblNewLabel, "2, 2, left, center");
		final JComboBox<Object> comboBox_importObj = new JComboBox<Object>(
				importObject);
		lblNewLabel.setLabelFor(comboBox_importObj);
		add(comboBox_importObj, "4, 2, 3, 1, fill, top");

		JButton btnImport = new JButton("Import");
		btnImport.setMnemonic('i');
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (path == null)
					return;
				// CSV file must be separated by comma (',')
				// sex(gender) fields must be "true" or "false"
				// Get csv file path
				String csv_filepath = CSVpath.getText();
				// Create new reader
				CSVReader reader = null;
				try {
					reader = new CSVReader(new FileReader(csv_filepath));
				} catch (FileNotFoundException e) {
					Control.getInstance()
							.getLogger()
							.log(Level.WARNING,
									"File not found: " + e.getMessage());
				}
				//
				String[] nextLine;
				try {
					if (comboBox_importObj.getSelectedIndex() == 0)
						while ((nextLine = reader.readNext()) != null) {
							// _insert_student(ct, first, last, sex, dob,
							// emails, phones, note, home, dt, point, cl, mssv,
							// year, k)
							String first = nextLine[0];
							String last = nextLine[1];
							boolean sex = Boolean.parseBoolean(nextLine[2]);
							String dob = nextLine[3];
							String emails = nextLine[4];
							String phones = nextLine[5];
							String note = nextLine[6];
							String home = nextLine[7];
							int dt = Integer.parseInt(nextLine[8]);
							double point = Double.parseDouble(nextLine[9]);
							int cl = Integer.parseInt(nextLine[10]);
							String mssv = nextLine[11];
							int year = Integer.parseInt(nextLine[12]);
							int k = Integer.parseInt(nextLine[13]);
							String message = (String) Control.getInstance()
									.getData("insertStudent", first, last, sex,
											dob, emails, phones, note, home,
											dt, point, cl, mssv, year, k);
							if (message != null)
								Control.getInstance()
										.getLogger()
										.log(Level.WARNING,
												"Insert student error. Message: "
														+ message);
						}
					if (comboBox_importObj.getSelectedIndex() == 1)
						while ((nextLine = reader.readNext()) != null) {
							// __insert_lecturer(ct integer, first text, last
							// text, sex boolean, dob date, emails text, phones
							// text, note text, home text, dt integer, fc
							// integer, dg integer)
							String first = nextLine[0];
							String last = nextLine[1];
							boolean sex = Boolean.parseBoolean(nextLine[2]);
							String dob = nextLine[3];
							String emails = nextLine[4];
							String phones = nextLine[5];
							String note = nextLine[6];
							String home = nextLine[7];
							int dt = Integer.parseInt(nextLine[8]);
							int fc = Integer.parseInt(nextLine[9]);
							int dg = Integer.parseInt(nextLine[10]);
							// "insert_lecturer", ct,
							// first, last, sex, dob, emails,
							// phones, note, home, dt, fc, dg
							String message = (String) Control.getInstance()
									.getData("insertLecturer", first, last,
											sex, dob, emails, phones, note,
											home, dt, fc, dg);
							if (message != null)
								Control.getInstance()
										.getLogger()
										.log(Level.WARNING,
												"Insert lecturer error. Message: "
														+ message);
						}
					if (comboBox_importObj.getSelectedIndex() == 2)
						while ((nextLine = reader.readNext()) != null) {
							// __insert_course_(ce integer, nfees double
							// precision, ncredits integer, topic text, ctime
							// double precision, name text, note text, code
							// text)
							double nfees = Double.parseDouble(nextLine[0]);
							int ncredits = Integer.parseInt(nextLine[1]);
							String topic = nextLine[2];
							double ctime = Double.parseDouble(nextLine[3]);
							String name = nextLine[4];
							String note = nextLine[5];
							String code = nextLine[6];
							// "insert_course_", ce, nfees, ncredits,
							// topic, ctime, name, note, code);
							String message = (String) Control.getInstance()
									.getData("insertCourse", nfees, ncredits,
											topic, ctime, name, note, code);
							if (message != null)
								Control.getInstance()
										.getLogger()
										.log(Level.WARNING,
												"Insert course error: "
														+ message);
						}
				} catch (IOException e) {
					Control.getInstance()
							.getLogger()
							.log(Level.WARNING,
									"IO errors. Message: " + e.getMessage());
				}
			}
		});

		JLabel lblCsvFilePath = new JLabel("CSV file path");
		add(lblCsvFilePath, "2, 4, left, center");

		CSVpath = new JTextField();
		CSVpath.setEditable(false);
		lblCsvFilePath.setLabelFor(CSVpath);
		add(CSVpath, "4, 4, fill, center");
		CSVpath.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				path = get_csv_file();
				CSVpath.setText(path);
			}
		});
		add(btnBrowse, "6, 4");

		add(btnImport, "2, 6, 5, 1, left, top");
	}

}
