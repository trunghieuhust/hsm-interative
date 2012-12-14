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
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
				JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
						"Name");
				add(lblName, "2, 2");
				lblName.setLabelFor(txtNameOfRoom);
				
						txtNameOfRoom = new JTextField();
						add(txtNameOfRoom, "4, 2, 3, 1");
						txtNameOfRoom.setToolTipText("Name of hedspiObject");
						txtNameOfRoom.setColumns(10);
		
				JButton btnSave = new JButton("Save");
				add(btnSave, "2, 4, left, default");
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
		
				JButton btnReload = new JButton("Reload");
				add(btnReload, "4, 4, left, default");
				btnReload.setToolTipText("Reload information from server");
				btnReload.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refresh();
					}
				});
		
				JButton btnExport = new JButton("Export");
				add(btnExport, "6, 4, left, default");
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
		
				JScrollPane scrollPane = new JScrollPane();
				
						JTable table = new JTable();
						table.setModel(dataModel);
						table.setToolTipText("List of classes held in hedspiObject");
						scrollPane.setViewportView(table);
						
								add(scrollPane, "2, 6, 5, 1, fill, fill");
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
