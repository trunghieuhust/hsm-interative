package org.hsm.view.main;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.hsm.view.course.CoursePane;
import org.hsm.view.district.DistrictPane;
import org.hsm.view.lecturer.LecturerPane;
import org.hsm.view.misc.MiscPane;
import org.hsm.view.room.RoomPane;
import org.hsm.view.student.StudentPane;
import org.hsm.view.teach.TeachPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblStatus;

	/**
	 * Create the panel.
	 */
	public MainPane() {
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("38px:grow"), },
				new RowSpec[] { RowSpec.decode("7px:grow"),
						FormFactory.DEFAULT_ROWSPEC, }));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		add(tabbedPane, "1, 1, fill, fill");

		StudentPane studentPanel = new StudentPane();
		tabbedPane.addTab("Student", null, studentPanel,
				"View and edit students' information grouped by class");

		LecturerPane lecturerPane = new LecturerPane();
		tabbedPane.addTab("Lecturer", null, lecturerPane,
				"View and edit lecturers' information grouped by faculty");

		TeachPane teachPane = new TeachPane();
		tabbedPane.addTab("Teaching", null, teachPane,
				"View and edit teaching information");

		DistrictPane districtPane = new DistrictPane();
		tabbedPane.addTab("District", null, districtPane,
				"Manage districts and cities' information");

		RoomPane roomPane = new RoomPane();
		tabbedPane.addTab("Room", null, roomPane, "Manage rooms' information");

		CoursePane coursePane = new CoursePane();
		tabbedPane.addTab("Course", null, coursePane, "View and edit cources");

		MiscPane miscPane = new MiscPane();
		tabbedPane
				.addTab("Misc", null, miscPane, "Various of useful utilities");

		JPanel statusPane = new JPanel();
		add(statusPane, "1, 2, fill, fill");
		statusPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		lblStatus = DefaultComponentFactory.getInstance().createLabel("Status");
		lblStatus.setFont(new Font("Dialog", Font.ITALIC, 12));
		statusPane.add(lblStatus, "4, 1");
	}

	public void setStatus(String status) {
		lblStatus.setText(status);
	}
}
