package org.hsm.view.room;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

public class RoomPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoomViewPane roomViewPane;

	/**
	 * Create the panel.
	 */
	public RoomPane() {
		
		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);
		
		ObjectListPane roomListPane = new ObjectListPane() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				roomViewPane.setRoom(value);
			}
			
			@Override
			public String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeRoom", value);
			}
			
			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance().getData("newRoom");
			}
			
			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData("getRoomList");
			}
		};
		
		scrollPane.setViewportView(roomListPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);
		
		roomViewPane = new RoomViewPane();
		scrollPane_1.setViewportView(roomViewPane);
		setDividerLocation(150);
	}
}
