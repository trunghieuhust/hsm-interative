package org.hsm.view.student;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiClass;
import org.hsm.model.hedspiObject.HedspiObject;

public class ClassViewPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HedspiClass hedspiClass;

	private ObjectListPane studentListPane;

	/**
	 * Create the panel.
	 */
	public ClassViewPane() {
		
		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);
		
		studentListPane = new ObjectListPane("Students list") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			void selectValue(HedspiObject value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeStudent", value);
			}
			
			@Override
			HedspiObject newElement() {
				return (HedspiObject)(Control.getInstance().getData("getNewRawStudentInClass", hedspiClass));
			}
			
			@Override
			HedspiObject[] getRefresh() {
				if (hedspiClass == null)
					return null;
				return (HedspiObject[])Control.getInstance().getData("getStudentRawListInClass", hedspiClass);
			}
		};
		scrollPane.setViewportView(studentListPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);
		
		JPanel studentViewPane = new JPanel();
		scrollPane_1.setViewportView(studentViewPane);
	}

	public void setHedspiClass(HedspiClass hedspiClass) {
		this.hedspiClass = hedspiClass;
		studentListPane.refresh();
	}
}
