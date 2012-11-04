package org.hsm.view.student;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;

public class StudentPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassViewPane classViewPane;

	/**
	 * Create the panel.
	 */
	public StudentPane() {

		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);

		ObjectListPane classListPane = new ObjectListPane("Class list") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance().getData("newClass");
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeClass",
						value);
			}

			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData(
						"classList");
			}

			@Override
			public void selectValue(HedspiObject value) {
				classViewPane.setHedspiClass(value);
			}

			@Override
			public String getTitle() {
				return "List of classes";
			}

		};
		classListPane.setToolTipText("List of classes");
		scrollPane.setViewportView(classListPane);

		classViewPane = new ClassViewPane();
		setRightComponent(classViewPane);
		setDividerLocation(150);
	}
}
