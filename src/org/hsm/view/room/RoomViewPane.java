package org.hsm.view.room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	private HedspiObject hedspiObject;
	private DefaultTableModel dataModel;

	/**
	 * Create the panel.
	 */
	public RoomViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

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
		txtNameOfRoom.setToolTipText("Name of hedspiObject");
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
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save hedspiObject's information");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				String name = txtNameOfRoom.getText();
				String message = (String) Control.getInstance().getData(
						"saveRoomName", hedspiObject, name);
				if (message == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save hedspiObject's name success", "Save success",
							JOptionPane.INFORMATION_MESSAGE);
					hedspiObject.setName(name);
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Save hedspiObject's name failed\nMessage: "
									+ message, "Save failed",
							JOptionPane.WARNING_MESSAGE);
			}
		});
		panel_1.add(btnSave, "2, 2");

		JButton btnReload = new JButton("Reload");
		btnReload.setToolTipText("Reload information from server");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		panel_1.add(btnReload, "4, 2");

		JButton btnExport = new JButton("Export");
		btnExport
				.setToolTipText("Export hedspiObject's information to html file");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of hedspiObject {"
								+ hedspiObject.getName() + "}", dataModel);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel_1.add(btnExport, "6, 2");

		JScrollPane scrollPane = new JScrollPane();

		JTable table = new JTable();
		dataModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"ID", "Course", "Lecturer" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(dataModel);
		table.setToolTipText("List of classes held in hedspiObject");
		scrollPane.setViewportView(table);

		add(scrollPane, "2, 6, fill, fill");
	}

	protected void refresh() {
		if (hedspiObject == null)
			return;
		String name = (String) Control.getInstance().getData("reloadRoomName",
				hedspiObject);
		if (name == null)
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Reload hedspiObject's name failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Reload failed", JOptionPane.WARNING_MESSAGE);
		else {
			txtNameOfRoom.setText(name);
			hedspiObject.setName(name);
		}

		// update table
		String[] arr = (String[]) Control.getInstance().getData(
				"getClassesInRoom", hedspiObject);
		for (int i = dataModel.getRowCount() - 1; i >= 0; i--)
			dataModel.removeRow(i);
		if (arr == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get classes in room failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);
			return;
		} else {
			for (int i = 0; i + 2 < arr.length; i += 3) {
				dataModel
						.addRow(new Object[] { arr[i], arr[i + 1], arr[i + 2] });
			}
		}
	}

	public void setHedspiObject(HedspiObject value) {
		hedspiObject = value;
		refresh();
	}
}
