package org.hsm.view.teach;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

public class TeachPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeachViewPane teachViewPane;

	/**
	 * Create the panel.
	 */
	public TeachPane() {

		ObjectListPane panel = new ObjectListPane("List of study classes") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				teachViewPane.setHedspiObject(value);
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData(
						"removeTeachingClass", value);
			}

			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance().getData(
						"getNewTeachingClass");
			}

			@Override
			public String getTitle() {
				return "List of all study classes";
			}

			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getRawListOfTeachingClasses");
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		setRightComponent(scrollPane);

		teachViewPane = new TeachViewPane();
		scrollPane.setViewportView(teachViewPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		setLeftComponent(scrollPane_1);

		scrollPane_1.setViewportView(panel);
		setDividerLocation(150);
	}
}
