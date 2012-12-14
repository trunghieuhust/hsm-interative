package org.hsm.view.district;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class DistrictViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private HedspiObject district;
	private DistrictStatisticPane districtStatisticPane;

	/**
	 * Create the panel.
	 */
	public DistrictViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
				"Name");
		add(lblName, "2, 2, left, default");

		textField = new JTextField();
		lblName.setLabelFor(textField);
		add(textField, "4, 2, 5, 1");
		textField.setToolTipText("Name of district");
		textField.setColumns(10);

		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic('s');
		add(btnSave, "2, 4, left, default");
		btnSave.setToolTipText("Save district information");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message;
				String name = textField.getText();
				if (district != null) {
					message = (String) Control.getInstance().getData(
							"saveDistrictName", district, name);
					if (message == null) {
						district.setName(name);
						JOptionPane.showMessageDialog(Control.getInstance()
								.getMainWindow(),
								"Save district's information successful",
								"Save successed",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(Control.getInstance()
								.getMainWindow(),
								"Save district's information failed\nMessage: "
										+ message, "Save failed",
								JOptionPane.ERROR_MESSAGE);
						district.setName(name);
					}
				}
			}
		});

		JButton btnReset = new JButton("Reset");
		btnReset.setMnemonic('t');
		add(btnReset, "4, 4, left, default");
		btnReset.setToolTipText("Reset to local value (last loaded from server)");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (district == null)
					return;
				textField.setText(district.getName());
			}
		});

		JButton btnReload = new JButton("Reload");
		btnReload.setMnemonic('l');
		add(btnReload, "6, 4, left, default");
		btnReload.setToolTipText("Reload value from server");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (district == null)
					return;
				String name = (String) Control.getInstance().getData(
						"getDistrictName", district);
				if (name == null)
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Reload name of district failed\nMessage: "
									+ Control.getInstance().getQueryMessage(),
							"Reload failed", JOptionPane.ERROR_MESSAGE);
				else {
					district.setName(name);
					textField.setText(district.getName());
				}
			}
		});

		JButton btnExport = new JButton("Export");
		btnExport.setMnemonic('x');
		add(btnExport, "8, 4, left, default");
		btnExport.setToolTipText("Export information to html file");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (district == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of district {" + district.getName() + "}",
						"Label", "value");
				hedspiTable.setIsTablePrint(false);
				hedspiTable.addValue("Name", textField.getText());
				HedspiObject city = (HedspiObject) Control.getInstance()
						.getData("getCityOfDistrict", district);
				hedspiTable.addValue("City",
						city == null ? "null" : city.getName());
				districtStatisticPane.export(hedspiTable);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});

		districtStatisticPane = new DistrictStatisticPane();
		districtStatisticPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(districtStatisticPane, "2, 6, 7, 1, fill, fill");
	}

	public void setDistrict(HedspiObject value) {
		if (value == null)
			return;
		this.district = value;
		textField.setText(value.getName());
		districtStatisticPane.setDistrict(value);
	}
}
