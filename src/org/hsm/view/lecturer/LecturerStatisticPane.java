package org.hsm.view.lecturer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class LecturerStatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldTeachedClasses;
	private JTextField textFieldTeachedStudents;
	private JTextField textFieldBestStudent;
	private JTextField textFieldWorstStudent;
	protected HedspiObject lecturer;
	private JTextField textFieldTeachedCourses;

	/**
	 * Create the panel.
	 */
	public LecturerStatisticPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		add(btnRefresh, "2, 2, center, default");

		JLabel lblTeachedClasses = DefaultComponentFactory.getInstance()
				.createLabel("Teached classes");
		add(lblTeachedClasses, "2, 4, right, default");

		textFieldTeachedClasses = new JTextField();
		lblTeachedClasses.setLabelFor(textFieldTeachedClasses);
		textFieldTeachedClasses.setEditable(false);
		add(textFieldTeachedClasses, "4, 4, fill, default");
		textFieldTeachedClasses.setColumns(10);

		JLabel lblTeachedCourses = DefaultComponentFactory.getInstance()
				.createLabel("Teached courses");
		add(lblTeachedCourses, "2, 6, right, default");

		textFieldTeachedCourses = new JTextField();
		lblTeachedCourses.setLabelFor(textFieldTeachedCourses);
		textFieldTeachedCourses.setEditable(false);
		textFieldTeachedCourses.setColumns(10);
		add(textFieldTeachedCourses, "4, 6, fill, default");

		JLabel lblTeachedStudents = DefaultComponentFactory.getInstance()
				.createLabel("Teached students");
		add(lblTeachedStudents, "2, 8, right, default");

		textFieldTeachedStudents = new JTextField();
		lblTeachedStudents.setLabelFor(textFieldTeachedStudents);
		textFieldTeachedStudents.setEditable(false);
		textFieldTeachedStudents.setColumns(10);
		add(textFieldTeachedStudents, "4, 8, fill, default");

		JLabel lblBestStudent = DefaultComponentFactory.getInstance()
				.createLabel("Best student");
		add(lblBestStudent, "2, 10, right, default");

		textFieldBestStudent = new JTextField();
		lblBestStudent.setLabelFor(textFieldBestStudent);
		textFieldBestStudent.setEditable(false);
		textFieldBestStudent.setColumns(10);
		add(textFieldBestStudent, "4, 10, fill, default");

		JLabel lblWorstStudent = DefaultComponentFactory.getInstance()
				.createLabel("Worst student");
		add(lblWorstStudent, "2, 12, right, default");

		textFieldWorstStudent = new JTextField();
		lblWorstStudent.setLabelFor(textFieldWorstStudent);
		textFieldWorstStudent.setEditable(false);
		textFieldWorstStudent.setColumns(10);
		add(textFieldWorstStudent, "4, 12, fill, default");
	}

	protected void refresh() {
		if (lecturer == null)
			return;
		String[] values = (String[]) Control.getInstance().getData(
				"getSingleLecturerStatistic", lecturer);
		if (values == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get lecturer's statistic information failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Refresh failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		textFieldTeachedClasses.setText(values[0]);
		textFieldTeachedCourses.setText(values[1]);
		textFieldTeachedStudents.setText(values[2]);
		textFieldBestStudent.setText(values[3]);
		textFieldWorstStudent.setText(values[4]);
	}

	public void setLecturer(HedspiObject hedspiObject) {
		this.lecturer = hedspiObject;
		refresh();
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Teached classes",
				textFieldTeachedClasses.getText());
		hedspiTable.addValue("Teached courses",
				textFieldTeachedCourses.getText());
		hedspiTable.addValue("Teached students",
				textFieldTeachedStudents.getText());
		hedspiTable.addValue("Best student", textFieldBestStudent.getText());
		hedspiTable.addValue("Worst student", textFieldWorstStudent.getText());
	}
}
