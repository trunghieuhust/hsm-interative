package org.hsm.view.teach;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;
import org.hsm.model.hedspiObject.Teach;
import org.hsm.view.student.HedspiComboBox;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class TeachViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiObject hedspiObject;
	private HedspiComboBox comboBoxCourse;
	private HedspiComboBox comboBoxLecturer;
	private HedspiComboBox comboBoxRoom;
	private JTextField textFieldCode;
	private JTable table;
	private DefaultTableModel dataModel;

	/**
	 * Create the panel.
	 */
	public TeachViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;

				int course = comboBoxCourse.getSelectedValue();
				int lecturer = comboBoxLecturer.getSelectedValue();
				int room = comboBoxRoom.getSelectedValue();
				if (course == -1 || lecturer == -1 || room == -1)
					return;

				Teach teach = new Teach(hedspiObject.getId(), textFieldCode
						.getText(), course, lecturer, room);
				String message = (String) Control.getInstance().getData(
						"saveTeachInfo", hedspiObject, teach);
				if (message == null) {
					hedspiObject.setName(teach.getName());
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save teaching class's information ok", "Save ok",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save teaching class's information failed\nMessage: "
									+ message, "Save failed",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSave.setToolTipText("Save study class's information");
		panel_1.add(btnSave, "1, 1");

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setToolTipText("Refresh data from server");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		panel_1.add(btnRefresh, "3, 1");

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable table = new HedspiTable(
						"Information of study class {"
								+ textFieldCode.getText() + "}", "label",
						"value");
				table.setIsTablePrint(false);
				table.addValue("Code", textFieldCode.getText());
				String[] ret = (String[]) Control.getInstance().getData(
						"getNameOfFactorsInTeachingClass", hedspiObject);
				if (ret == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Get name of factors in teaching class failed\nMessage: "
									+ Control.getInstance().getQueryMessage(),
							"Get data failed", JOptionPane.WARNING_MESSAGE);
					return;
				}

				String courseName = ret[0];
				String lecturerName = ret[1];
				String roomName = ret[2];
				table.addValue("Course", courseName);
				table.addValue("Lecturer", lecturerName);
				table.addValue("Room", roomName);

				HedspiTable studentTable = new HedspiTable(
						"List of students in class {" + textFieldCode.getText()
								+ "}", dataModel);
				table.writeToHtmlWithMessageDialog(studentTable);
			}
		});
		btnExport.setToolTipText("Export to html format");
		panel_1.add(btnExport, "5, 1");

		JPanel panel_2 = new JPanel();
		add(panel_2, "2, 4, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblCode = DefaultComponentFactory.getInstance().createLabel(
				"Code");
		panel_2.add(lblCode, "1, 1, right, default");
		lblCode.setLabelFor(textFieldCode);

		textFieldCode = new JTextField();
		panel_2.add(textFieldCode, "3, 1, fill, default");
		textFieldCode.setToolTipText("Unique code of study class");
		textFieldCode.setColumns(10);

		JLabel lblCourse = new JLabel("Course");
		panel_2.add(lblCourse, "1, 3, right, default");

		comboBoxCourse = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getCoursesList");
			}
		};
		panel_2.add(comboBoxCourse, "3, 3, fill, fill");
		comboBoxCourse.setToolTipText("Choose course of class");
		lblCourse.setLabelFor(comboBoxCourse);

		JLabel lblLecturer = DefaultComponentFactory.getInstance().createLabel(
				"Lecturer");
		panel_2.add(lblLecturer, "1, 5, right, default");

		comboBoxLecturer = new HedspiComboBox() {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getLecturerListAll");
			}
		};
		panel_2.add(comboBoxLecturer, "3, 5, fill, default");
		comboBoxLecturer.setToolTipText("Choose lecturer who teach the class");
		lblLecturer.setLabelFor(comboBoxLecturer);

		JLabel lblRoom = DefaultComponentFactory.getInstance().createLabel(
				"Room");
		panel_2.add(lblRoom, "1, 7, right, default");

		comboBoxRoom = new HedspiComboBox() {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getRoomList");
			}
		};
		panel_2.add(comboBoxRoom, "3, 7, fill, default");
		comboBoxRoom.setToolTipText("Choose room that course will be held in");
		lblRoom.setLabelFor(comboBoxRoom);

		JLabel lblStudentsList = DefaultComponentFactory.getInstance()
				.createLabel("Students list");
		add(lblStudentsList, "2, 6");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("List of students in class");
		lblStudentsList.setLabelFor(scrollPane);
		add(scrollPane, "2, 8, fill, fill");

		table = new JTable();
		dataModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"MSSV", "Full name", "Class" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(dataModel);
		table.setToolTipText("");
		scrollPane.setViewportView(table);
	}

	public void setHedspiObject(HedspiObject value) {
		if (value == null)
			return;
		this.hedspiObject = value;
		refresh();
	}

	private void refresh() {
		if (hedspiObject == null)
			return;
		Teach teach = (Teach) Control.getInstance().getData(
				"getFullDataOfTeachingClass", hedspiObject);
		if (teach == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get full data of teaching class failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Refresh data failed", JOptionPane.WARNING_MESSAGE);
		} else {
			textFieldCode.setText(teach.getName());
			comboBoxCourse.setSelectedValue(teach.getCourse());
			comboBoxLecturer.setSelectedValue(teach.getLecturer());
			comboBoxRoom.setSelectedValue(teach.getRoom());
		}

		String[] studentList = (String[]) Control.getInstance().getData(
				"getListOfStudentsInTeachingClass", hedspiObject);
		if (studentList == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get list of student in class failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Refresh failed", JOptionPane.WARNING_MESSAGE);
		} else {
			int cnt = dataModel.getRowCount();
			for (int i = cnt - 1; i >= 0; i--)
				dataModel.removeRow(i);
			for (int i = 0; i + 2 < studentList.length;) {
				String mssv = studentList[i];
				String name = studentList[i + 1];
				String cl = studentList[i + 2];
				i += 3;
				dataModel.addRow(new Object[] { mssv, name, cl });
			}
		}
	}
}
