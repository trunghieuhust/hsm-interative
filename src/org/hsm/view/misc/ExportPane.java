package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.hsm.control.Control;
import org.hsm.model.Model;
import org.hsm.service.CoreService;

public class ExportPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_exportList;
	private JTextField textField_exportPath;

	/**
	 * Create the panel.
	 */
	public ExportPane() {
		setLayout(new MigLayout("", "[][157.00][grow]", "[][][][][][][][]"));

		String[] exportObject = { "Students", "Lecturers", "Courses" };

		JLabel lblExport = new JLabel("EXPORT");
		add(lblExport, "cell 0 0");

		JLabel lblExportObject = new JLabel("Export object");
		add(lblExportObject, "cell 0 1");
		final JComboBox<Object> comboBox_exportObj = new JComboBox<Object>(
				exportObject);
		lblExportObject.setLabelFor(comboBox_exportObj);
		add(comboBox_exportObj, "cell 1 1 2 1,growx");

		JLabel lblExportFields = new JLabel("Export fields");
		add(lblExportFields, "cell 0 2");

		final JCheckBox chckbxPersonalDetails = new JCheckBox(
				"Personal details");
		chckbxPersonalDetails.setMnemonic('p');
		add(chckbxPersonalDetails, "cell 1 2");

		final JCheckBox chckbxInfo = new JCheckBox("Academic Information");
		chckbxInfo.setMnemonic('f');
		add(chckbxInfo, "cell 2 2");

		final JCheckBox chckbxContact = new JCheckBox("Contact");
		chckbxContact.setMnemonic('o');
		add(chckbxContact, "cell 1 3");

		final JCheckBox chckbxNote = new JCheckBox("Note");
		chckbxNote.setMnemonic('n');
		add(chckbxNote, "cell 2 3");

		JLabel lblExportList = new JLabel("Export list");
		add(lblExportList, "cell 0 4");

		textField_exportList = new JTextField();
		lblExportList.setLabelFor(textField_exportList);
		add(textField_exportList, "cell 1 4 2 1,growx");
		textField_exportList.setColumns(10);

		final JCheckBox chckbxAll = new JCheckBox("All");
		chckbxAll.setMnemonic('a');
		add(chckbxAll, "cell 1 5 2 1");

		JButton btnExport = new JButton("Export");
		btnExport.setMnemonic('x');
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* Export students */
				// html_content is whole content to write to .html
				if (comboBox_exportObj.getSelectedIndex() == 0) {
					String html_content = "<html>\n" + "<head>\n"
							+ "<title>HEDSPI student</title>\n" + "</head>\n";
					html_content += "<body>\n";
					// -- Create the table
					html_content += "<table border=\"1\">\n"
							+ "<tr bgcolor=\"cccccc\">\n";
					if (chckbxPersonalDetails.isSelected()) {
						html_content += "<td>Fullname</td>\n"
								+ "<td>DOB</td>\n" + "<td>Gender</td>\n";
					}
					if (chckbxInfo.isSelected()) {
						html_content += "<td>MSSV</td>\n" + "<td>Year</td>\n"
								+ "<td>K</td>\n" + "<td>Class</td>\n"
								+ "<td>Point</td>\n";
					}
					if (chckbxContact.isSelected()) {
						html_content += "<td>Email</td>\n" + "<td>Tel</td>\n"
								+ "<td>District</td>\n" + "<td>Address</td>\n";
					}
					if (chckbxNote.isSelected()) {
						html_content += "<td>Note</td>\n";
					}
					html_content += "</tr>\n";
					// If "Export All" is not selected, check out list of
					// student
					if (!chckbxAll.isSelected()) {
						String raw_list = textField_exportList.getText();
						// break all ";"
						String[] parts = raw_list.split(";");
						// Search for all student code in list
						for (int i = 0; i < parts.length; i++) {
							ArrayList<HashMap<String, Object>> rs = CoreService
									.getInstance().doQueryFunction(
											"get_student_by_mssv", parts[i]);
							HashMap<String, Object> ret = rs.get(0);
							html_content += "<tr>";
							if (chckbxPersonalDetails.isSelected()) {
								// Fullname
								String first = (String) ret.get("first");
								if (first == null)
									first = "";
								String last = (String) ret.get("last");
								if (last == null)
									last = "";
								html_content += "<td>" + first + last
										+ "</td>\n";
								// Gender
								String gender = "";
								boolean isMale;
								if (ret.get("sex") == null)
									isMale = true;
								else
									isMale = (boolean) ret.get("sex");
								if (isMale)
									gender = "Male";
								else
									gender = "Female";
								html_content += "<td>" + gender + "</td>\n";
								// DOB
								Date dob = (Date) ret.get("dob");
								if (dob == null)
									dob = new Date();
								html_content += "<td>" + dob + "</td>\n";
							}
							if (chckbxInfo.isSelected()) {
								// MSSV
								String mssv = (String) ret.get("mssv");
								if (mssv == null)
									mssv = "";
								html_content += "<td>" + mssv + "</td>\n";
								// Year
								int year;
								if (ret.get("year") == null)
									year = 0;
								else
									year = (int) ret.get("year");
								html_content += "<td>" + year + "</td>\n";
								// K
								int k;
								if (ret.get("k") == null)
									k = 0;
								else
									k = (int) ret.get("k");
								html_content += "<td>" + k + "</td>\n";
								// point
								double point;
								if (ret.get("point") == null)
									point = 0;
								else
									point = (double) ret.get("point");
								html_content += "<td>" + point + "</td>\n";
								// class
								int hedspiClass;
								if (ret.get("cl") == null)
									hedspiClass = 0;
								else
									hedspiClass = (int) ret.get("cl");
								html_content += "<td>" + hedspiClass
										+ "</td>\n";
							}
							// Contact
							if (chckbxContact.isSelected()) {
								// emails
								String[] emails;
								String emailsStr = (String) ret.get("emails");
								if (emailsStr == null)
									emailsStr = "";
								emails = emailsStr.split("\n");
								html_content += "<td>";
								for (String _emails : emails)
									html_content += _emails + "<br>";
								html_content += "</td>\n";
								// phones
								String[] phones;
								String phonesStr = (String) ret.get("phones");
								if (phonesStr == null)
									phonesStr = "";
								phones = phonesStr.split("\n");
								html_content += "<td>";
								for (String _phones : phones)
									html_content += _phones + "<br>";
								html_content += "</td>";
								int dt;
								if (ret.get("dt") == null)
									dt = -1;
								else
									dt = (int) ret.get("dt");
								// System.out.print((String)
								// Model.getInstance().getData("getDistrictName",
								// dt));
								html_content += "<td>"
										+ (String) Model.getInstance().getData(
												"getDistrictName", dt)
										+ "</td>";
								// home
								String home = (String) ret.get("home");
								if (home == null)
									home = "";
								html_content += "<td>" + home + "</td>\n";
							}
							if (chckbxNote.isSelected()) {
								String note = (String) ret.get("note");
								if (note == null)
									note = "";
								html_content += "<td>" + note + "</td>\n";
							}
						}
					} else {
						// If "Export all" checked, export list of all student
						ArrayList<HashMap<String, Object>> rs = CoreService
								.getInstance().doQueryFunction(
										"get_all_students");
						for (int i = 0; i < rs.size(); i++) {
							HashMap<String, Object> ret = rs.get(i);
							html_content += "<tr>";
							// MSSV
							if (chckbxPersonalDetails.isSelected()) {
								// Fullname
								String first = (String) ret.get("first");
								if (first == null)
									first = "";
								String last = (String) ret.get("last");
								if (last == null)
									last = "";
								html_content += "<td>" + first + last
										+ "</td>\n";
								// Gender
								String gender = "";
								boolean isMale;
								if (ret.get("sex") == null)
									isMale = true;
								else
									isMale = (boolean) ret.get("sex");
								if (isMale)
									gender = "Male";
								else
									gender = "Female";
								html_content += "<td>" + gender + "</td>\n";
								// DOB
								Date dob = (Date) ret.get("dob");
								if (dob == null)
									dob = new Date();
								html_content += "<td>" + dob + "</td>\n";
							}
							if (chckbxInfo.isSelected()) {
								// MSSV
								String mssv = (String) ret.get("mssv");
								if (mssv == null)
									mssv = "";
								html_content += "<td>" + mssv + "</td>\n";
								// Year
								int year;
								if (ret.get("year") == null)
									year = 0;
								else
									year = (int) ret.get("year");
								html_content += "<td>" + year + "</td>\n";
								// K
								int k;
								if (ret.get("k") == null)
									k = 0;
								else
									k = (int) ret.get("k");
								html_content += "<td>" + k + "</td>\n";
								// class
								int hedspiClass;
								if (ret.get("cl") == null)
									hedspiClass = 0;
								else
									hedspiClass = (int) ret.get("cl");
								html_content += "<td>" + hedspiClass
										+ "</td>\n";
								// point
								double point;
								if (ret.get("point") == null)
									point = 0;
								else
									point = (double) ret.get("point");
								html_content += "<td>" + point + "</td>\n";
							}
							// Contact
							if (chckbxContact.isSelected()) {
								// emails
								String[] emails;
								String emailsStr = (String) ret.get("emails");
								if (emailsStr == null)
									emailsStr = "";
								emails = emailsStr.split("\n");
								html_content += "<td>";
								for (String _emails : emails)
									html_content += _emails + "<br>";
								html_content += "</td>\n";
								// phones
								String[] phones;
								String phonesStr = (String) ret.get("phones");
								if (phonesStr == null)
									phonesStr = "";
								phones = phonesStr.split("\n");
								html_content += "<td>";
								for (String _phones : phones)
									html_content += _phones + "<br>";
								html_content += "</td>";
								// district
								int dt;
								if (ret.get("dt") == null)
									dt = -1;
								else
									dt = (int) ret.get("dt");
								html_content += "<td>"
										+ Model.getInstance().getData(
												"getDistrictName", dt)
										+ "</td>";
								// home
								String home = (String) ret.get("home");
								if (home == null)
									home = "";
								html_content += "<td>" + home + "</td>\n";
							}
							if (chckbxNote.isSelected()) {
								String note = (String) ret.get("note");
								if (note == null)
									note = "";
								html_content += "<td>" + note + "</td>\n";
							}
							html_content += "</tr>";
						}
					}
					html_content += "</tr>\n" + "</table>\n" + "</body>\n"
							+ "</html>\n";

					try {
						// Write html_content to file
						String file_path = textField_exportPath.getText();
						// Create file
						FileWriter fstream = new FileWriter(file_path);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(html_content);
						// Close the output stream
						out.close();
					} catch (IOException e) {
						Control.getInstance()
								.getLogger()
								.log(Level.WARNING,
										"Export error. Message: "
												+ e.getMessage());
					}
				}
				// --------------------------------------------------------------------
				// Export Lecturer
				// --------------------------------------------------------------------
				if (comboBox_exportObj.getSelectedIndex() == 1) {
					String html_content = "<html>\n" + "<head>\n"
							+ "<title>HEDSPI lecturers</title>\n" + "</head>\n";
					html_content += "<body>\n";
					// -- Create the table
					html_content += "<table border=\"1\">\n"
							+ "<tr bgcolor=\"cccccc\">\n";
					if (chckbxPersonalDetails.isSelected()) {
						html_content += "<td>Fullname</td>\n"
								+ "<td>DOB</td>\n" + "<td>Gender</td>\n";
					}
					if (chckbxInfo.isSelected()) {
						html_content += "<td>Faculty</td>\n"
								+ "<td>Year</td>\n" + "<td>Degree</td>\n";
					}
					if (chckbxContact.isSelected()) {
						html_content += "<td>Email</td>\n" + "<td>Tel</td>\n"
								+ "<td>District</td>\n" + "<td>Address</td>\n";
					}
					if (chckbxNote.isSelected()) {
						html_content += "<td>Note</td>\n";
					}
					html_content += "</tr>\n";
					// If "Export All" is not selected, check out list of
					// student
					if (!chckbxAll.isSelected()) {
						String raw_list = textField_exportList.getText();
						// break all ";"
						String[] parts = raw_list.split(";");
						// Search for all student code in list
						for (int i = 0; i < parts.length; i++) {
							ArrayList<HashMap<String, Object>> rs = CoreService
									.getInstance().doQueryFunction(
											"get_lecturer_by_fullname",
											parts[i]);
							HashMap<String, Object> ret = rs.get(0);
							html_content += "<tr>";
							if (chckbxPersonalDetails.isSelected()) {
								// Fullname
								String first = (String) ret.get("first");
								if (first == null)
									first = "";
								String last = (String) ret.get("last");
								if (last == null)
									last = "";
								html_content += "<td>" + first + last
										+ "</td>\n";
								// Gender
								String gender = "";
								int _gender = (int) ret.get("sex");
								if (_gender == 1)
									gender = "Male";
								else
									gender = "Female";
								html_content += "<td>" + gender + "</td>\n";
								// DOB
								Date dob = (Date) ret.get("dob");
								if (dob == null)
									dob = new Date();
								html_content += "<td>" + dob + "</td>\n";
							}
							if (chckbxInfo.isSelected()) {
								// faculty
								int fc;
								if (ret.get("k") == null)
									fc = 0;
								else
									fc = (int) ret.get("k");
								html_content += "<td>" + fc + "</td>\n";
								// degree
								int dg;
								if (ret.get("cl") == null)
									dg = 0;
								else
									dg = (int) ret.get("cl");
								html_content += "<td>" + dg + "</td>\n";
							}
							// Contact
							if (chckbxContact.isSelected()) {
								// emails
								String[] emails;
								String emailsStr = (String) ret.get("emails");
								if (emailsStr == null)
									emailsStr = "";
								emails = emailsStr.split("\n");
								html_content += "<td>";
								for (String _emails : emails)
									html_content += _emails + "<br>";
								html_content += "</td>\n";
								// phones
								String[] phones;
								String phonesStr = (String) ret.get("phones");
								if (phonesStr == null)
									phonesStr = "";
								phones = phonesStr.split("\n");
								html_content += "<td>";
								for (String _phones : phones)
									html_content += _phones + "<br>";
								html_content += "</td>";
								// district
								int dt;
								if (ret.get("dt") == null)
									dt = -1;
								else
									dt = (int) ret.get("dt");
								html_content += "<td>"
										+ Model.getInstance().getData(
												"getDistrictName", dt)
										+ "</td>";
								// home
								String home = (String) ret.get("home");
								if (home == null)
									home = "";
								html_content += "<td>" + home + "</td>\n";
							}
							// note
							if (chckbxNote.isSelected()) {
								String note = (String) ret.get("note");
								if (note == null)
									note = "";
								html_content += "<td>" + note + "</td>\n";
							}
						}
					} else {
						// If "Export all" checked, export list of all student
						ArrayList<HashMap<String, Object>> rs = CoreService
								.getInstance().doQueryFunction(
										"get_all_lecturers");
						for (int i = 0; i < rs.size(); i++) {
							HashMap<String, Object> ret = rs.get(i);
							html_content += "<tr>";
							if (chckbxPersonalDetails.isSelected()) {
								// Fullname
								String first = (String) ret.get("first");
								if (first == null)
									first = "";
								String last = (String) ret.get("last");
								if (last == null)
									last = "";
								html_content += "<td>" + first + last
										+ "</td>\n";
								// Gender
								String gender = "";
								int _gender = (int) ret.get("sex");
								if (_gender == 1)
									gender = "Male";
								else
									gender = "Female";
								html_content += "<td>" + gender + "</td>\n";
								// DOB
								Date dob = (Date) ret.get("dob");
								if (dob == null)
									dob = new Date();
								html_content += "<td>" + dob + "</td>\n";
							}
							if (chckbxInfo.isSelected()) {
								// faculty
								int fc;
								if (ret.get("k") == null)
									fc = 0;
								else
									fc = (int) ret.get("k");
								html_content += "<td>" + fc + "</td>\n";
								// degree
								int dg;
								if (ret.get("cl") == null)
									dg = 0;
								else
									dg = (int) ret.get("cl");
								html_content += "<td>" + dg + "</td>\n";
							}
							// Contact
							if (chckbxContact.isSelected()) {
								// emails
								String[] emails;
								String emailsStr = (String) ret.get("emails");
								if (emailsStr == null)
									emailsStr = "";
								emails = emailsStr.split("\n");
								html_content += "<td>";
								for (String _emails : emails)
									html_content += _emails + "<br>";
								html_content += "</td>\n";
								// phones
								String[] phones;
								String phonesStr = (String) ret.get("phones");
								if (phonesStr == null)
									phonesStr = "";
								phones = phonesStr.split("\n");
								html_content += "<td>";
								for (String _phones : phones)
									html_content += _phones + "<br>";
								html_content += "</td>";
								// district
								int dt;
								if (ret.get("dt") == null)
									dt = -1;
								else
									dt = (int) ret.get("dt");
								html_content += "<td>"
										+ Model.getInstance().getData(
												"getDistrictName", dt)
										+ "</td>";
								// home
								String home = (String) ret.get("home");
								if (home == null)
									home = "";
								html_content += "<td>" + home + "</td>\n";
							}
							if (chckbxNote.isSelected()) {
								String note = (String) ret.get("note");
								if (note == null)
									note = "";
								html_content += "<td>" + note + "</td>\n";
							}
							html_content += "</tr>";
						}
					}
					html_content += "</tr>\n" + "</table>\n" + "</body>\n"
							+ "</html>\n";

					// Write html_content to file
					try {
						String file_path = textField_exportPath.getText();
						// Create file
						FileWriter fstream = new FileWriter(file_path);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(html_content);
						// Close the output stream
						out.close();
					} catch (IOException e) {// Catch exception if any
						Control.getInstance()
								.getLogger()
								.log(Level.WARNING,
										"IO Error: " + e.getMessage());
					}
				}
				// --------------------------------------------------
				// Export courses list
				// --------------------------------------------------
				if (comboBox_exportObj.getSelectedIndex() == 2) {

					String html_content = "<html>\n" + "<head>\n"
							+ "<title>HEDSPI lecturers</title>\n" + "</head>\n";
					html_content += "<body>\n";
					// -- Create the table
					html_content += "<table border=\"1\">\n"
							+ "<tr bgcolor=\"cccccc\">\n";

					html_content += "<td>Course name</td>\n"
							+ "<td>Code</td>\n" + "<td>Topic</td>\n"
							+ "<td>Time</td>\n" + "<td>Credits</td>\n"
							+ "<td>Fees</td>\n" + "<td>CE</td>\n";

					html_content += "</tr>\n";
					if (!chckbxAll.isSelected()) {
						String raw_list = textField_exportList.getText();
						System.out.print(raw_list);
						// break all ";"
						String[] parts = raw_list.split(";");
						for (int i = 0; i < parts.length; i++) {
							ArrayList<HashMap<String, Object>> rs = CoreService
									.getInstance().doQueryFunction(
											"get_course_by_code", parts[i]);
							HashMap<String, Object> ret = rs.get(0);
							html_content += "<tr>";
							// coursename
							String coursename = (String) ret.get("name");
							if (coursename == null)
								coursename = "";
							html_content += "<td>" + coursename + "</td>\n";
							// coursecode
							String coursecode = (String) ret.get("code");
							if (coursecode == null)
								coursecode = "";
							html_content += "<td>" + coursecode + "</td>\n";
							// coursetopic
							String coursetopic = (String) ret.get("topic");
							if (coursetopic == null)
								coursetopic = "";
							html_content += "<td>" + coursetopic + "</td>\n";
							// time
							double ctime;
							if (ret.get("ctime") == null)
								ctime = 0;
							else
								ctime = (double) ret.get("ctime");
							html_content += "<td>" + ctime + "</td>\n";
							// credits
							int ncredits;
							if (ret.get("ncredits") == null)
								ncredits = 0;
							else
								ncredits = (int) ret.get("ncredits");
							html_content += "<td>" + ncredits + "</td>\n";
							// fees
							double fees;
							if (ret.get("fees") == null)
								fees = 0;
							else
								fees = (double) ret.get("fees");
							html_content += "<td>" + fees + "</td>\n";
							// ce
							String ce = (String) ret.get("ce");
							if (ce == null)
								ce = "";
							html_content += "<td>" + ce + "</td>\n";
							// notes
							String notes = (String) ret.get("notes");
							if (notes == null)
								notes = "";
							html_content += "<td>" + notes + "</td>\n";
						}
					} else {
						ArrayList<HashMap<String, Object>> rs = CoreService
								.getInstance().doQueryFunction(
										"get_all_courses");
						for (int i = 0; i < rs.size(); i++) {
							HashMap<String, Object> ret = rs.get(i);
							html_content += "<tr>";
							// coursename
							String coursename = (String) ret.get("name");
							if (coursename == null)
								coursename = "";
							html_content += "<td>" + coursename + "</td>\n";
							// coursecode
							String coursecode = (String) ret.get("code");
							if (coursecode == null)
								coursecode = "";
							html_content += "<td>" + coursecode + "</td>\n";
							// coursetopic
							String coursetopic = (String) ret.get("topic");
							if (coursetopic == null)
								coursetopic = "";
							html_content += "<td>" + coursetopic + "</td>\n";
							// time
							double ctime;
							if (ret.get("ctime") == null)
								ctime = 0;
							else
								ctime = (double) ret.get("ctime");
							html_content += "<td>" + ctime + "</td>\n";
							// credits
							int ncredits;
							if (ret.get("ncredits") == null)
								ncredits = 0;
							else
								ncredits = (int) ret.get("ncredits");
							html_content += "<td>" + ncredits + "</td>\n";
							// fees
							double fees;
							if (ret.get("fees") == null)
								fees = 0;
							else
								fees = (double) ret.get("fees");
							html_content += "<td>" + fees + "</td>\n";
							// ce
							String ce = (String) ret.get("ce");
							if (ce == null)
								ce = "";
							html_content += "<td>" + ce + "</td>\n";
							// notes
							String notes = (String) ret.get("notes");
							if (notes == null)
								notes = "";
							html_content += "<td>" + notes + "</td>\n";

							html_content += "</tr>";
						}
					}
					html_content += "</tr>\n" + "</table>\n" + "</body>\n"
							+ "</html>\n";

					// Write html_content to file
					try {
						String file_path = textField_exportPath.getText();
						// Create file
						FileWriter fstream = new FileWriter(file_path);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(html_content);
						// Close the output stream
						out.close();
					} catch (Exception e) {// Catch exception if any
						System.err.println("Error: " + e.getMessage());
					}
				}
			}
		});

		JLabel lblExportPath = new JLabel("Export path");
		add(lblExportPath, "cell 0 6");

		textField_exportPath = new JTextField();
		lblExportPath.setLabelFor(textField_exportPath);
		add(textField_exportPath, "cell 1 6 2 1,growx");
		textField_exportPath.setColumns(10);

		add(btnExport, "cell 0 7 3 1,alignx left");

	}

}
