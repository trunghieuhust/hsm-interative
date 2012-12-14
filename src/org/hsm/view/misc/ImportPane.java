package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.service.CoreService;

import au.com.bytecode.opencsv.CSVReader;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class ImportPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField CSVpath;

	/**
	 * Create the panel.
	 */
	public ImportPane() {

		String[] importObject = { "Students", "Lecturers", "Courses" };
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("94px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("96px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("24px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("Import object");
		add(lblNewLabel, "2, 2, left, center");
		final JComboBox<Object> comboBox_importObj = new JComboBox<Object>(
				importObject);
		lblNewLabel.setLabelFor(comboBox_importObj);
		add(comboBox_importObj, "4, 2, fill, top");
		
				JButton btnImport = new JButton("Import");
				btnImport.setMnemonic('i');
				btnImport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
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
									int ct = Integer.parseInt(nextLine[0]);
									String first = nextLine[1];
									String last = nextLine[2];
									boolean sex = Boolean.parseBoolean(nextLine[3]);
									String dob = nextLine[4];
									String emails = nextLine[5];
									String phones = nextLine[6];
									String note = nextLine[7];
									String home = nextLine[8];
									int dt = Integer.parseInt(nextLine[9]);
									double point = Double.parseDouble(nextLine[10]);
									int cl = Integer.parseInt(nextLine[11]);
									String mssv = nextLine[12];
									int year = Integer.parseInt(nextLine[13]);
									int k = Integer.parseInt(nextLine[14]);
									CoreService.getInstance().doQueryFunction(
											"insert_student", ct, first, last, sex,
											dob, emails, phones, note, home, dt, point,
											cl, mssv, year, k);
								}
							if (comboBox_importObj.getSelectedIndex() == 1)
								while ((nextLine = reader.readNext()) != null) {
									// __insert_lecturer(ct integer, first text, last
									// text, sex boolean, dob date, emails text, phones
									// text, note text, home text, dt integer, fc
									// integer, dg integer)
									int ct = Integer.parseInt(nextLine[0]);
									String first = nextLine[1];
									String last = nextLine[2];
									boolean sex = Boolean.parseBoolean(nextLine[3]);
									String dob = nextLine[4];
									String emails = nextLine[5];
									String phones = nextLine[6];
									String note = nextLine[7];
									String home = nextLine[8];
									int dt = Integer.parseInt(nextLine[9]);
									int fc = Integer.parseInt(nextLine[10]);
									int dg = Integer.parseInt(nextLine[11]);
									CoreService.getInstance()
											.doQueryFunction("insert_lecturer", ct,
													first, last, sex, dob, emails,
													phones, note, home, dt, fc, dg);
								}
							if (comboBox_importObj.getSelectedIndex() == 2)
								while ((nextLine = reader.readNext()) != null) {
									// __insert_course_(ce integer, nfees double
									// precision, ncredits integer, topic text, ctime
									// double precision, name text, note text, code
									// text)
									int ce = Integer.parseInt(nextLine[0]);
									double nfees = Double.parseDouble(nextLine[1]);
									int ncredits = Integer.parseInt(nextLine[3]);
									String topic = nextLine[3];
									double ctime = Double.parseDouble(nextLine[4]);
									String name = nextLine[5];
									String note = nextLine[6];
									String code = nextLine[7];
									CoreService.getInstance().doQueryFunction(
											"insert_course_", ce, nfees, ncredits,
											topic, ctime, name, note, code);
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
						lblCsvFilePath.setLabelFor(CSVpath);
						add(CSVpath, "4, 4, fill, center");
						CSVpath.setColumns(10);
				add(btnImport, "2, 6, 3, 1, left, top");
	}

}
