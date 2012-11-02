package org.hsm.view.district;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CityViewPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiObject city;
	private DistrictViewPane districtViewPane;
	private ObjectListPane districtListPane;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public CityViewPane() {

		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "2, 2, fill, top");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (city == null)
					return;
				String name = textField.getText();
				String message = (String) Control.getInstance().getData(
						"saveCityName", city, name);
				if (message == null) {
					city.setName(name);
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save city's name success",
							"Save name success",
							JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save city's name failed\nMessage: " + message,
							"Save name failed", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel_1.add(btnSave, "2, 2");

		textField = new JTextField();
		panel_1.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		districtListPane = new ObjectListPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				districtViewPane.setDistrict(value);
			}

			@Override
			public HedspiObject newElement() {
				if (city == null)
					return null;
				return (HedspiObject) Control.getInstance().getData(
						"newDistrict", city);
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeDistrict",
						value);
			}

			@Override
			public HedspiObject[] getRefresh() {
				if (city == null)
					return new HedspiObject[0];
				return (HedspiObject[]) Control.getInstance().getData(
						"getDistrictsListInCity", city);
			}

			@Override
			public String getTitle() {
				if (city == null)
					return null;
				return "Districts list of city {" + city.getName() + "}";
			}
		};
		panel.add(districtListPane, "2, 4, fill, fill");

		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);

		districtViewPane = new DistrictViewPane();
		scrollPane_1.setViewportView(districtViewPane);
		setDividerLocation(150);
	}

	public void setCity(HedspiObject value) {
		this.city = value;
		if (value != null) {
			textField.setText(value.getName());
			districtListPane.refresh();
		}
	}
}
