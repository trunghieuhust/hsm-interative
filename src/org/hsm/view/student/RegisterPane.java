package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.AcademicInfo;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public abstract class RegisterPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private HedspiObject hedspiObject;
	private HedspiComboBox comboboxClassList;
	private JCheckBox chckbxIncludeContactInfo;

	/**
	 * Create the panel.
	 */
	public RegisterPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(17dlu;default):grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		comboboxClassList = new HedspiComboBox() {

			/**
									 * 
									 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getRawListOfTeachingClasses");
			}
		};
		panel_1.add(comboboxClassList, "2, 2");
		comboboxClassList.setEnabled(false);

		JPanel panel = new JPanel();
		add(panel, "2, 4, fill, fill");
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save register status of student to server");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				ArrayList<AcademicInfo> arr = new ArrayList<>();
				for (int i = 0; i < model.getRowCount(); i++) {
					arr.add(new AcademicInfo((HedspiObject) model.getValueAt(i,
							0), (Boolean) model.getValueAt(i, 1),
							(Double) model.getValueAt(i, 2)));
				}
				String message = (String) Control.getInstance().getData(
						"saveAcademicInfo", hedspiObject,
						arr.toArray(new AcademicInfo[arr.size()]));
				if (message == null)
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Update student academic's information success",
							"Update success", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Update student academic's information failed.\nMessage: "
									+ message, "Update failed",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton button = new JButton("+");
		button.setToolTipText("Add course");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HedspiObject teach = comboboxClassList.getSelectedObject();
				if (teach == null)
					return;
				model.addRow(new Object[] { teach, false, new Double(0) });
			}
		});
		panel.add(button, "2, 1");

		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelected();
			}
		});
		panel.add(button_1, "4, 1");
		panel.add(btnSave, "6, 1");

		JButton btnExportToHtml = new JButton("Export");
		panel.add(btnExportToHtml, "8, 1");
		btnExportToHtml
				.setToolTipText("Export student's register status to html file");

		chckbxIncludeContactInfo = new JCheckBox("Include contact info");
		chckbxIncludeContactInfo
				.setToolTipText("Check whether to include contact information in export form");
		chckbxIncludeContactInfo.setSelected(true);
		panel.add(chckbxIncludeContactInfo, "10, 1");
		btnExportToHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Academic status of student { "
								+ hedspiObject.getName() + "}", model);
				if (chckbxIncludeContactInfo.isSelected()) {
					HedspiTable infoTable = getStudentViewPane()
							.getHedspiTable();
					if (infoTable == null)
						hedspiTable.writeToHtmlWithMessageDialog();
					else
						infoTable.writeToHtmlWithMessageDialog(hedspiTable);
				} else {
					hedspiTable.writeToHtmlWithMessageDialog();
				}
			}
		});
		model = new DefaultTableModel(new Object[][] {}, new String[] {
				"Class", "Is passed", "Result" }) {
			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { HedspiTable.class,
					Boolean.class, Double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setToolTipText("Current student's register status. Right click for more options");
		add(scrollPane, "2, 6, fill, fill");

		table = new JTable();
		table.setToolTipText("Current student's register status. Right click for more options");
		scrollPane.setViewportView(table);
		table.setModel(model);
	}

	abstract StudentViewPane getStudentViewPane();

	private void deleteSelected() {
		int[] indices = table.getSelectedRows();
		Arrays.sort(indices);
		for (int i = indices.length - 1; i >= 0; i--) {
			model.removeRow(indices[i]);
		}
	}

	public void setHedpiObject(HedspiObject value) {
		if (value == null)
			return;
		this.hedspiObject = value;
		AcademicInfo[] academicInfo = (AcademicInfo[]) Control.getInstance()
				.getData("getAcademicInfo", value);
		int cnt = model.getRowCount();
		for (int i = cnt - 1; i >= 0; i--)
			model.removeRow(i);
		for (AcademicInfo it : academicInfo)
			model.addRow(new Object[] { it.getTeach(), it.isPassed(),
					it.getResult() });
	}
}