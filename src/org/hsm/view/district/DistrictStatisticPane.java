package org.hsm.view.district;

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

public class DistrictStatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNLecturers;
	private JTextField textFieldNStudents;
	private JTextField textFieldBestStudent;
	private JTextField textFieldWorstStudent;
	private HedspiObject district;

	/**
	 * Create the panel.
	 */
	public DistrictStatisticPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setMnemonic('r');
		btnRefresh.setToolTipText("Refresh values");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		add(btnRefresh, "2, 2, 3, 1, left, default");

		JLabel lblNumberOfLecturers = DefaultComponentFactory.getInstance()
				.createLabel("Number of lecturers");
		add(lblNumberOfLecturers, "2, 4, right, default");

		textFieldNLecturers = new JTextField();
		textFieldNLecturers
				.setToolTipText("Number of lecturers living in district");
		lblNumberOfLecturers.setLabelFor(textFieldNLecturers);
		textFieldNLecturers.setEditable(false);
		add(textFieldNLecturers, "4, 4, fill, default");
		textFieldNLecturers.setColumns(10);

		JLabel lblNumberOfStudents = DefaultComponentFactory.getInstance()
				.createLabel("Number of students");
		add(lblNumberOfStudents, "2, 6, right, default");

		textFieldNStudents = new JTextField();
		textFieldNStudents
				.setToolTipText("Number of students living in district");
		lblNumberOfStudents.setLabelFor(textFieldNStudents);
		textFieldNStudents.setEditable(false);
		textFieldNStudents.setColumns(10);
		add(textFieldNStudents, "4, 6, fill, default");

		JLabel lblBestStudent = DefaultComponentFactory.getInstance()
				.createLabel("Best student");
		add(lblBestStudent, "2, 8, right, default");

		textFieldBestStudent = new JTextField();
		textFieldBestStudent
				.setToolTipText("Name of student having best academic result");
		lblBestStudent.setLabelFor(textFieldBestStudent);
		textFieldBestStudent.setEditable(false);
		textFieldBestStudent.setColumns(10);
		add(textFieldBestStudent, "4, 8, fill, default");

		JLabel lblWorstStudent = DefaultComponentFactory.getInstance()
				.createLabel("Worst student");
		add(lblWorstStudent, "2, 10, right, default");

		textFieldWorstStudent = new JTextField();
		textFieldWorstStudent
				.setToolTipText("Name of student having worst academic result");
		lblWorstStudent.setLabelFor(textFieldWorstStudent);
		textFieldWorstStudent.setEditable(false);
		textFieldWorstStudent.setColumns(10);
		add(textFieldWorstStudent, "4, 10, fill, default");
	}

	protected void refresh() {
		if (district == null)
			return;
		String[] rr = (String[]) Control.getInstance().getData(
				"getSingleDistrictStatistic", district);
		if (rr == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Refresh district's statistic information failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Refresh faileD", JOptionPane.WARNING_MESSAGE);
			return;
		}
		textFieldNLecturers.setText(rr[0]);
		textFieldNStudents.setText(rr[1]);
		textFieldBestStudent.setText(rr[2]);
		textFieldWorstStudent.setText(rr[3]);
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Number of lecturers",
				textFieldNLecturers.getText());
		hedspiTable
				.addValue("Number of students", textFieldNStudents.getText());
		hedspiTable.addValue("Best student", textFieldBestStudent.getText());
		hedspiTable.addValue("Worst student", textFieldWorstStudent.getText());
	}

	public void setDistrict(HedspiObject value) {
		district = value;
		refresh();
	}

}
