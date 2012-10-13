package org.hsm.view.lecturer;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

public class LecturerPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacultyViewPane facultyViewPane;

	/**
	 * Create the panel.
	 */
	public LecturerPane() {
		
		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);
		
		ObjectListPane facultyListPane = new ObjectListPane("Faculties list"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				facultyViewPane.setFaculty(value);
			}

			@Override
			public HedspiObject newElement() {
				return (HedspiObject)Control.getInstance().getData("newFaculty");
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeFaculty", value);
			}

			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData("getListOfFaculties");
			}};
		scrollPane.setViewportView(facultyListPane);
		
		facultyViewPane = new FacultyViewPane();
		setRightComponent(facultyViewPane);
		setDividerLocation(150);
	}
}
