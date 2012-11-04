package org.hsm.view.room;

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

public class RoomViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNameOfRoom;
	private HedspiObject room;
	private RoomStatisticPane roomStatisticPane;

	/**
	 * Create the panel.
	 */
	public RoomViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
				"Name");
		panel.add(lblName, "1, 1, right, default");

		txtNameOfRoom = new JTextField();
		txtNameOfRoom.setToolTipText("Name of room");
		lblName.setLabelFor(txtNameOfRoom);
		panel.add(txtNameOfRoom, "3, 1, fill, default");
		txtNameOfRoom.setColumns(10);

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 4, center, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save room's information");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (room == null)
					return;
				String name = txtNameOfRoom.getText();
				String message = (String) Control.getInstance().getData(
						"saveRoomName", room, name);
				if (message == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save room's name success",
							"Save success", JOptionPane.INFORMATION_MESSAGE);
					room.setName(name);
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save room's name failed\nMessage: " + message,
							"Save failed", JOptionPane.WARNING_MESSAGE);
			}
		});
		panel_1.add(btnSave, "2, 2");

		JButton btnReset = new JButton("Reset");
		btnReset.setToolTipText("Reset room's information to latest loaded value from server");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRoom(room);
			}
		});
		panel_1.add(btnReset, "4, 2");

		JButton btnReload = new JButton("Reload");
		btnReload.setToolTipText("Reload information from server");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (room == null)
					return;
				String name = (String) Control.getInstance().getData(
						"reloadRoomName", room);
				if (name == null)
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Reload room's name failed\nMessage: "
									+ Control.getInstance().getQueryMessage(),
							"Reload failed", JOptionPane.WARNING_MESSAGE);
				else {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Reload room's name success",
							"Reload success", JOptionPane.INFORMATION_MESSAGE);
					txtNameOfRoom.setText(name);
					room.setName(name);
				}
			}
		});
		panel_1.add(btnReload, "6, 2");

		JButton btnExport = new JButton("Export");
		btnExport.setToolTipText("Export room's information to html file");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (room == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of room {" + room.getName() + "}",
						"label", "value");
				hedspiTable.setIsTablePrint(false);
				hedspiTable.addValue("Name", txtNameOfRoom.getText());
				roomStatisticPane.export(hedspiTable);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel_1.add(btnExport, "8, 2");

		roomStatisticPane = new RoomStatisticPane();
		roomStatisticPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(roomStatisticPane, "2, 6, fill, fill");
	}

	public void setRoom(HedspiObject value) {
		if (value != null) {
			room = value;
			txtNameOfRoom.setText(value.getName());
			roomStatisticPane.setRoom(value);
		}
	}
}
