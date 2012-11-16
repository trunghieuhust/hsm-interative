package org.hsm.view.lecturer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public abstract class LecturerClassesListPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	protected HedspiObject hedspiObject;
	private DefaultTableModel dataModel;
	private JCheckBox chckbxIncludeContactInfo;

	/**
	 * Create the panel.
	 */
	public LecturerClassesListPane() {
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
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		btnRefresh.setToolTipText("Reload data from server");
		btnRefresh.setMnemonic('r');
		panel.add(btnRefresh, "1, 1");

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable table = new HedspiTable(
						"Classes teached by lecturer {"
								+ hedspiObject.getName() + "}", dataModel);
				if (chckbxIncludeContactInfo.isSelected()) {
					HedspiTable contactTable = getLecturerViewPane()
							.getHedspiTable();
					if (contactTable == null)
						table.writeToHtmlWithMessageDialog();
					else
						contactTable.writeToHtmlWithMessageDialog(table);
				} else {
					table.writeToHtmlWithMessageDialog();
				}
			}
		});
		btnExport.setMnemonic('x');
		panel.add(btnExport, "3, 1");
		btnExport.setToolTipText("Export to html format");

		chckbxIncludeContactInfo = new JCheckBox("Include contact info");
		chckbxIncludeContactInfo.setMnemonic('i');
		panel.add(chckbxIncludeContactInfo, "5, 1");
		chckbxIncludeContactInfo.setSelected(true);

		JLabel lblListOfClasses = new JLabel("List of classes");
		add(lblListOfClasses, "2, 4");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 6, fill, fill");
		table = new JTable();
		dataModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"ID", "Course", "Room" }) {
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
		lblListOfClasses.setLabelFor(table);
		table.setToolTipText("List of classes");
		scrollPane.setViewportView(table);
	}

	abstract LecturerViewPane getLecturerViewPane();

	protected void refresh() {
		if (hedspiObject == null)
			return;
		String[] arr = (String[]) Control.getInstance().getData(
				"getLTeachedInfoOfLecturer", hedspiObject);
		if (arr == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get teaching information of lecturer failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		for (int i = dataModel.getRowCount() - 1; i >= 0; i--) {
			dataModel.removeRow(i);
		}
		for (int i = 0; i + 2 < arr.length; i += 2) {
			dataModel.addRow(new Object[] { arr[i], arr[i + 1], arr[i + 2] });
		}
	}

	public void setHedspiObject(HedspiObject value) {
		hedspiObject = value;
		refresh();
	}

}
