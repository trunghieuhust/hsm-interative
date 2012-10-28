package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hsm.model.hedspiObject.HedspiObject;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public abstract class HedspiComboBox extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel<HedspiObject> comboBoxModel;
	private JComboBox<HedspiObject> comboBox;
	private JButton btnR;

	/**
	 * Create the panel.
	 */
	public HedspiComboBox() {
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		comboBoxModel = new DefaultComboBoxModel<>();
		comboBox = new JComboBox<>(comboBoxModel);
		add(comboBox, "1, 1, fill, default");

		btnR = new JButton("R");
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// save current id
				HedspiObject obj = (HedspiObject) comboBox.getSelectedItem();

				comboBoxModel.removeAllElements();
				HedspiObject[] values = getValues();
				if (values == null)
					JOptionPane.showMessageDialog(null, "Refresh failed",
							"Refresh failed", JOptionPane.ERROR_MESSAGE);
				else
					for (HedspiObject it : values)
						comboBoxModel.addElement(it);

				if (obj != null)
					setSelectedValue(obj.getId());
			}
		});
		add(btnR, "3, 1");

		refresh();
	}

	public abstract HedspiObject[] getValues();

	public void setSelectedValue(int id) {
		for (int i = 0; i < comboBoxModel.getSize(); i++)
			if (comboBoxModel.getElementAt(i).getId() == id) {
				comboBox.setSelectedIndex(i);
				break;
			}
	}

	public int getSelectedValue() {
		HedspiObject obj = (HedspiObject) comboBoxModel.getSelectedItem();
		if (obj == null)
			return -1;
		else
			return obj.getId();
	}

	public HedspiObject getSelectedObject() {
		return (HedspiObject) comboBoxModel.getSelectedItem();
	}

	public JComboBox<HedspiObject> getComboBox() {
		return comboBox;
	}

	public void refresh() {
		btnR.doClick();
	}

	public void removeObject(HedspiObject selected) {
		comboBoxModel.removeElement(selected);
	}

	public void addObject(HedspiObject it) {
		comboBoxModel.addElement(it);
	}
}
