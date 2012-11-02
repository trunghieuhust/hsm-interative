package org.hsm.view.room;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomStatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private HedspiObject room;

	/**
	 * Create the panel.
	 */
	public RoomStatisticPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		add(btnRefresh, "2, 2, center, default");
		
		JLabel lblNumberOfClasses = DefaultComponentFactory.getInstance().createLabel("Number of classes held in");
		add(lblNumberOfClasses, "2, 4, right, default");
		
		textField = new JTextField();
		lblNumberOfClasses.setLabelFor(textField);
		textField.setEditable(false);
		add(textField, "4, 4, fill, default");
		textField.setColumns(10);
	}

	protected void refresh() {
		if (room == null)
			return;
		int cnt = (int)Control.getInstance().getData("getNClassesInRoom", room);
		textField.setText(String.valueOf(cnt));
	}

	public void setRoom(HedspiObject value) {
		this.room = value;
		refresh();
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Number of classes held in", textField.getText());
	}

}
