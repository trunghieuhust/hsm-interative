package org.hsm.view.course;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.course.visual.HierachyView;
import org.hsm.view.student.ObjectListPane;

public class CoursePane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CourseViewPane courseViewPane;
	private HierachyView hierachyView;

	/**
	 * Create the panel.
	 */
	public CoursePane() {

		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);

		ObjectListPane courseListPane = new ObjectListPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				Course course = (Course) Control.getInstance().getData(
						"getFullDataCourse", value);
				if (course == null) {
					JOptionPane.showMessageDialog(Control.getInstance().getMainWindow(),
							"Get information of course failed\nMessage: " + Control.getInstance().getQueryMessage(),
							"Load data failed", JOptionPane.WARNING_MESSAGE);
				} else {
					courseViewPane.setHedspiObject(value);
					courseViewPane.setInfo(course);
					hierachyView.setHedspiObject(value);
				}

			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeCourse",
						value);
			}

			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance()
						.getData("newCourse");
			}

			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getCoursesList");
			}
		};
		scrollPane.setViewportView(courseListPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setRightComponent(tabbedPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Course's information", null, scrollPane_1, null);

		courseViewPane = new CourseViewPane();
		scrollPane_1.setViewportView(courseViewPane);
		
		hierachyView = new HierachyView();
		tabbedPane.addTab("Visual Hierachical tree", null, hierachyView, null);
		setDividerLocation(150);
	}
}
