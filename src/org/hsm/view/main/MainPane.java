package org.hsm.view.main;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.hsm.view.course.CoursePane;
import org.hsm.view.district.DistrictPane;
import org.hsm.view.imex.ImexPane;
import org.hsm.view.lecturer.LecturerPane;
import org.hsm.view.maintain.MaintainPane;
import org.hsm.view.register.RegisterPane;
import org.hsm.view.room.RoomPane;
import org.hsm.view.student.StudentPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MainPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("38px:grow"),},
			new RowSpec[] {
				RowSpec.decode("7px:grow"),}));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		add(tabbedPane, "1, 1, fill, fill");
		
		StudentPane studentPanel = new StudentPane();
		tabbedPane.addTab("Student", null, studentPanel, null);
		
		LecturerPane lecturerPanel = new LecturerPane();
		tabbedPane.addTab("Lecturer", null, lecturerPanel, null);
		
		DistrictPane districtPane = new DistrictPane();
		tabbedPane.addTab("District", null, districtPane, null);
		
		RoomPane roomPane = new RoomPane();
		tabbedPane.addTab("Room", null, roomPane, null);
		
		CoursePane coursePane = new CoursePane();
		tabbedPane.addTab("Course", null, coursePane, null);
		
		RegisterPane registerPane = new RegisterPane();
		tabbedPane.addTab("Register", null, registerPane, null);
		
		ImexPane imexPanel = new ImexPane();
		tabbedPane.addTab("Im-Export", null, imexPanel, null);
		
		MaintainPane maintainPane = new MaintainPane();
		tabbedPane.addTab("Maintain", null, maintainPane, null);
	}
}
