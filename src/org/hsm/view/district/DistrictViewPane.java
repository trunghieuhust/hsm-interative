package org.hsm.view.district;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DistrictViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private HedspiObject district;

	/**
	 * Create the panel.
	 */
	public DistrictViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = DefaultComponentFactory.getInstance().createLabel("Name");
		add(lblName, "2, 2, right, default");
		
		textField = new JTextField();
		add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message;
				String name = textField.getText();
				if (district != null){
					message = (String)Control.getInstance().getData("saveDistrictName", district, name);
					if (message == null){
						district.setName(name);
						JOptionPane.showMessageDialog(null, "Save district's information successful", "Save successed", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, "Save district's information failed\nMessage: " + message, "Save failed", JOptionPane.ERROR_MESSAGE);
						district.setName(name);
					}
				}
			}
		});
		add(btnSave, "6, 2");
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (district == null)
					return;
				textField.setText(district.getName());
			}
		});
		add(btnReset, "8, 2");
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (district == null)
					return;
				String name = (String)Control.getInstance().getData("getDistrictName", district);
				if (name == null)
					JOptionPane.showMessageDialog(null, "Reload name of district failed", "Reload failed", JOptionPane.ERROR_MESSAGE);
				else{
					district.setName(name);
					textField.setText(district.getName());
				}
			}
		});
		add(btnReload, "10, 2");
	}

	public void setDistrict(HedspiObject value) {
		this.district = value;
		if (value != null)
			textField.setText(value.getName());
	}
}
