package org.hsm.view.course;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

public class CoursePane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CourseViewPane courseViewPane;

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
				Course course = (Course) Control.getInstance().getData("getFullDataCourse", value);
				if (course == null){
					JOptionPane.showMessageDialog(null, "Get information of course failed", "Load data failed", JOptionPane.WARNING_MESSAGE);
				}
				else{
					courseViewPane.setHedspiObject(value);
					courseViewPane.setInfo(course);
				}
					
			}
			
			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeCourse", value);
			}
			
			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance().getData("newCourse");
			}
			
			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData("getCoursesList");
			}
		};
		scrollPane.setViewportView(courseListPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);
		
		courseViewPane = new CourseViewPane();
		scrollPane_1.setViewportView(courseViewPane);
		setDividerLocation(150);
	}
}
