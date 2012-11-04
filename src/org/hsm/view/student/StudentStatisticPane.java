package org.hsm.view.student;

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

public class StudentStatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiObject student;
	private JTextField textFieldPassedClasses;
	private JTextField textFieldFailedClasses;
	private JTextField textFieldPassedCourse;
	private JTextField textFieldFailedCourses;
	private JTextField textFieldMaxPoint;
	private JTextField textFieldMinPoint;
	private JTextField textFieldAveragePoint;
	private JTextField textFieldAverageMax;

	/**
	 * Create the panel.
	 */
	public StudentStatisticPane() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("min:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setToolTipText("Refresh student's statistic information");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		add(btnRefresh, "2, 2, center, default");

		JLabel lblPassedClasses = DefaultComponentFactory.getInstance()
				.createLabel("Passed classes");
		add(lblPassedClasses, "2, 4, right, default");

		textFieldPassedClasses = new JTextField();
		textFieldPassedClasses
				.setToolTipText("Number of classes that student passed");
		lblPassedClasses.setLabelFor(textFieldPassedClasses);
		textFieldPassedClasses.setEditable(false);
		textFieldPassedClasses.setColumns(10);
		add(textFieldPassedClasses, "4, 4, fill, default");

		JLabel lblFailedClass = DefaultComponentFactory.getInstance()
				.createLabel("Failed class");
		add(lblFailedClass, "2, 6, right, default");

		textFieldFailedClasses = new JTextField();
		textFieldFailedClasses
				.setToolTipText("Number of classes that student got failed");
		lblFailedClass.setLabelFor(textFieldFailedClasses);
		textFieldFailedClasses.setEditable(false);
		textFieldFailedClasses.setColumns(10);
		add(textFieldFailedClasses, "4, 6, fill, default");

		JLabel lblPassedCourse = DefaultComponentFactory.getInstance()
				.createLabel("Passed course");
		add(lblPassedCourse, "2, 10, right, default");

		textFieldPassedCourse = new JTextField();
		textFieldPassedCourse
				.setToolTipText("Number of courses that student passed");
		lblPassedCourse.setLabelFor(textFieldPassedCourse);
		textFieldPassedCourse.setEditable(false);
		textFieldPassedCourse.setColumns(10);
		add(textFieldPassedCourse, "4, 10, fill, default");

		JLabel lblFailedCourses = DefaultComponentFactory.getInstance()
				.createLabel("Failed courses");
		add(lblFailedCourses, "2, 12, right, default");

		textFieldFailedCourses = new JTextField();
		textFieldFailedCourses
				.setToolTipText("Number of courses that student got failed");
		lblFailedCourses.setLabelFor(textFieldFailedCourses);
		textFieldFailedCourses.setEditable(false);
		textFieldFailedCourses.setColumns(10);
		add(textFieldFailedCourses, "4, 12, fill, default");

		JLabel lblMaxPoint = DefaultComponentFactory.getInstance().createLabel(
				"Max point");
		add(lblMaxPoint, "2, 16, right, default");

		textFieldMaxPoint = new JTextField();
		textFieldMaxPoint
				.setToolTipText("The best point that student achieved");
		lblMaxPoint.setLabelFor(textFieldMaxPoint);
		textFieldMaxPoint.setEditable(false);
		textFieldMaxPoint.setColumns(10);
		add(textFieldMaxPoint, "4, 16, fill, default");

		JLabel lblMinPoint = DefaultComponentFactory.getInstance().createLabel(
				"Min point");
		add(lblMinPoint, "2, 18, right, default");

		textFieldMinPoint = new JTextField();
		textFieldMinPoint.setToolTipText("The worst point that student got");
		lblMinPoint.setLabelFor(textFieldMinPoint);
		textFieldMinPoint.setEditable(false);
		textFieldMinPoint.setColumns(10);
		add(textFieldMinPoint, "4, 18, fill, default");

		JLabel lblAveragePoint = DefaultComponentFactory.getInstance()
				.createLabel("Average point");
		add(lblAveragePoint, "2, 20, right, default");

		textFieldAveragePoint = new JTextField();
		textFieldAveragePoint
				.setToolTipText("The average point which student has");
		lblAveragePoint.setLabelFor(textFieldAveragePoint);
		textFieldAveragePoint.setEditable(false);
		textFieldAveragePoint.setColumns(10);
		add(textFieldAveragePoint, "4, 20, fill, default");

		JLabel lblAverageOfMax = DefaultComponentFactory.getInstance()
				.createLabel("Average of max point");
		add(lblAverageOfMax, "2, 22, right, default");

		textFieldAverageMax = new JTextField();
		textFieldAverageMax
				.setToolTipText("The average of maximal points in all course he attended");
		lblAverageOfMax.setLabelFor(textFieldAverageMax);
		textFieldAverageMax.setEditable(false);
		textFieldAverageMax.setColumns(10);
		add(textFieldAverageMax, "4, 22, fill, default");
	}

	public void refresh() {
		if (student == null)
			return;
		Number[] statistic = (Number[]) Control.getInstance().getData(
				"getStatisticOfStudent", student);
		if (statistic == null)
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Failed to refresh student statistic information\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);
		else {
			textFieldPassedClasses.setText(String.valueOf(statistic[0]
					.intValue()));
			textFieldFailedClasses.setText(String.valueOf(statistic[1]
					.intValue()));
			textFieldPassedCourse.setText(String.valueOf(statistic[2]
					.intValue()));
			textFieldFailedCourses.setText(String.valueOf(statistic[3]
					.intValue()));
			textFieldMaxPoint
					.setText(String.valueOf(statistic[4].doubleValue()));
			textFieldMinPoint
					.setText(String.valueOf(statistic[5].doubleValue()));
			textFieldAveragePoint.setText(String.valueOf(statistic[6]
					.doubleValue()));
			textFieldAverageMax.setText(String.valueOf(statistic[7]
					.doubleValue()));
		}
	}

	public void setStudent(HedspiObject student) {
		this.student = student;
		refresh();
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable
				.addValue("Passed classes", textFieldPassedClasses.getText());
		hedspiTable
				.addValue("Failed classes", textFieldFailedClasses.getText());
		hedspiTable.addValue("Passed courses", textFieldPassedCourse.getText());
		hedspiTable
				.addValue("Failed courses", textFieldFailedCourses.getText());
		hedspiTable.addValue("Min point", textFieldMinPoint.getText());
		hedspiTable.addValue("Max point", textFieldMaxPoint.getText());
		hedspiTable.addValue("Average of point",
				textFieldAveragePoint.getText());
		hedspiTable.addValue("Average of max point",
				textFieldAverageMax.getText());
	}

}
