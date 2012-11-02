package org.hsm.view.course;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CourseStatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldLecturers;
	private JTextField textFieldPassedStudents;
	private JTextField textFieldFailedStudents;
	private JTextField textFieldBestPoint;
	private JTextField textFieldWorstPoint;
	private JTextField textFieldAveragePoint;
	private JTextField textFieldBestStudent;
	private JTextField textFieldWorstStudent;
	protected HedspiObject course;

	/**
	 * Create the panel.
	 */
	public CourseStatisticPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		add(btnRefresh, "2, 2, center, default");
		
		JLabel lblTeachedLecturers = DefaultComponentFactory.getInstance().createLabel("Teached lecturers");
		add(lblTeachedLecturers, "2, 4, right, default");
		
		textFieldLecturers = new JTextField();
		lblTeachedLecturers.setLabelFor(textFieldLecturers);
		textFieldLecturers.setEditable(false);
		add(textFieldLecturers, "4, 4, fill, default");
		textFieldLecturers.setColumns(10);
		
		JLabel lblPassedStudents = DefaultComponentFactory.getInstance().createLabel("Passed students");
		add(lblPassedStudents, "2, 6, right, default");
		
		textFieldPassedStudents = new JTextField();
		lblPassedStudents.setLabelFor(textFieldPassedStudents);
		textFieldPassedStudents.setEditable(false);
		textFieldPassedStudents.setColumns(10);
		add(textFieldPassedStudents, "4, 6, fill, default");
		
		JLabel lblFailedStudents = DefaultComponentFactory.getInstance().createLabel("Failed students");
		add(lblFailedStudents, "2, 8, right, default");
		
		textFieldFailedStudents = new JTextField();
		lblFailedStudents.setLabelFor(textFieldFailedStudents);
		textFieldFailedStudents.setEditable(false);
		textFieldFailedStudents.setColumns(10);
		add(textFieldFailedStudents, "4, 8, fill, default");
		
		JLabel lblBestPoint = DefaultComponentFactory.getInstance().createLabel("Best point");
		add(lblBestPoint, "2, 12, right, default");
		
		textFieldBestPoint = new JTextField();
		lblBestPoint.setLabelFor(textFieldBestPoint);
		textFieldBestPoint.setEditable(false);
		textFieldBestPoint.setColumns(10);
		add(textFieldBestPoint, "4, 12, fill, default");
		
		JLabel lblWorstPoint = DefaultComponentFactory.getInstance().createLabel("Worst point");
		add(lblWorstPoint, "2, 14, right, default");
		
		textFieldWorstPoint = new JTextField();
		lblWorstPoint.setLabelFor(textFieldWorstPoint);
		textFieldWorstPoint.setEditable(false);
		textFieldWorstPoint.setColumns(10);
		add(textFieldWorstPoint, "4, 14, fill, default");
		
		JLabel lblAveragePoint = DefaultComponentFactory.getInstance().createLabel("Average point");
		add(lblAveragePoint, "2, 16, right, default");
		
		textFieldAveragePoint = new JTextField();
		lblAveragePoint.setLabelFor(textFieldAveragePoint);
		textFieldAveragePoint.setEditable(false);
		textFieldAveragePoint.setColumns(10);
		add(textFieldAveragePoint, "4, 16, fill, default");
		
		JLabel lblBestStudent = DefaultComponentFactory.getInstance().createLabel("Best student");
		add(lblBestStudent, "2, 20, right, default");
		
		textFieldBestStudent = new JTextField();
		lblBestStudent.setLabelFor(textFieldBestStudent);
		textFieldBestStudent.setEditable(false);
		textFieldBestStudent.setColumns(10);
		add(textFieldBestStudent, "4, 20, fill, default");
		
		JLabel lblWorstStudent = DefaultComponentFactory.getInstance().createLabel("Worst student");
		add(lblWorstStudent, "2, 22, right, default");
		
		textFieldWorstStudent = new JTextField();
		lblWorstStudent.setLabelFor(textFieldWorstStudent);
		textFieldWorstStudent.setEditable(false);
		textFieldWorstStudent.setColumns(10);
		add(textFieldWorstStudent, "4, 22, fill, default");
	}

	protected void refresh() {
		if (course == null)
			return;
		String[] val = (String[]) Control.getInstance().getData("getSingleCourseStatistic", course);
		if (val == null){
			JOptionPane.showMessageDialog(Control.getInstance().getMainWindow(), "Get course statistic failed\nMessage: " + Control.getInstance().getQueryMessage(), "Refresh failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		textFieldLecturers.setText(val[0]);
		textFieldPassedStudents.setText(val[1]);
		textFieldFailedStudents.setText(val[2]);
		textFieldBestPoint.setText(val[3]);
		textFieldWorstPoint.setText(val[4]);
		textFieldAveragePoint.setText(val[5]);
		textFieldBestStudent.setText(val[6]);
		textFieldWorstStudent.setText(val[7]);
	}

	public void setCourse(HedspiObject value) {
		this.course = value;
		refresh();
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Number of teached lecturers", textFieldLecturers.getText());
		hedspiTable.addValue("Number of passed students", textFieldPassedStudents.getText());
		hedspiTable.addValue("Number of failed students", textFieldFailedStudents.getText());
		
		hedspiTable.addValue("Maximum point", textFieldBestPoint.getText());
		hedspiTable.addValue("Minmimum point", textFieldWorstPoint.getText() );
		hedspiTable.addValue("Average point", textFieldAveragePoint.getText());
		
		hedspiTable.addValue("Best student", textFieldBestStudent.getText());
		hedspiTable.addValue("Worst student", textFieldWorstStudent.getText());
	}

}
