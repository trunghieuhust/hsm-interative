package org.hsm.view.district;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

public class DistrictMainPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CityViewPane cityViewPane;

	/**
	 * Create the panel.
	 */
	public DistrictMainPane() {
		
		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);
		
		ObjectListPane cityListPane = new ObjectListPane("Cities list"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public
			HedspiObject newElement() {
				return (HedspiObject)Control.getInstance().getData("newCity");
			}

			@Override
			public
			String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeCity", value);
			}

			@Override
			public
			HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData("getCitiesList");
			}

			@Override
			public
			void selectValue(HedspiObject value) {
				cityViewPane.setCity(value);
			}
	
		};
			
		scrollPane.setViewportView(cityListPane);
		
		cityViewPane = new CityViewPane();
		setRightComponent(cityViewPane);
		setDividerLocation(150);
	}
}
