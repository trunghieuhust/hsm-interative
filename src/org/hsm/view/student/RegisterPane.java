package org.hsm.view.student;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.AcademicInfo;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class RegisterPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private HedspiComboBox comboBoxCourse;
	private HedspiComboBox comboBoxLecturer;
	private HedspiComboBox comboBoxRoom;
	private DefaultTableModel model;
	private JPopupMenu popupMenu;
	private HedspiObject hedspiObject;

	/**
	 * Create the panel.
	 */
	public RegisterPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<AcademicInfo> arr = new ArrayList<>();
				for (int i = 0; i < model.getRowCount(); i++)
					arr.add(new AcademicInfo((HedspiObject) model.getValueAt(i,
							0), (HedspiObject) model.getValueAt(i, 1),
							(HedspiObject) model.getValueAt(i, 2),
							((Boolean) model.getValueAt(i, 3)).booleanValue(),
							((Double) model.getValueAt(i, 4)).doubleValue()));
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

		JButton btnExportToHtml = new JButton("Export");
		btnExportToHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Academic status of student { "
								+ hedspiObject.getName() + "}", "Course",
						"Lecturer", "Room", "Is passed", "Result");
				int cnt = model.getRowCount();
				for (int i = 0; i < cnt; i++)
					hedspiTable.addValue(
							((HedspiObject) model.getValueAt(i, 0)).getName(),
							((HedspiObject) model.getValueAt(i, 1)).getName(),
							((HedspiObject) model.getValueAt(i, 2)).getName(),
							((boolean) model.getValueAt(i, 3)) ? "Yes" : "No",
							String.valueOf((double) model.getValueAt(i, 4)));
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel.add(btnExportToHtml, "2, 2");
		panel.add(btnSave, "2, 4");

		JLabel lblCourse = DefaultComponentFactory.getInstance().createLabel(
				"Course");
		panel.add(lblCourse, "4, 4");

		JLabel lblLecturer = DefaultComponentFactory.getInstance().createLabel(
				"Lecturer");
		panel.add(lblLecturer, "6, 4");

		JLabel lblRoom = DefaultComponentFactory.getInstance().createLabel(
				"Room");
		panel.add(lblRoom, "8, 4");

		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HedspiObject course = comboBoxCourse.getSelectedObject();
				HedspiObject lecturer = comboBoxLecturer.getSelectedObject();
				HedspiObject room = comboBoxRoom.getSelectedObject();
				if (course == null || lecturer == null || room == null)
					return;
				model.addRow(new Object[] { course, lecturer, room,
						new Boolean(false), new Double(0) });
			}
		});
		panel.add(button, "2, 6");

		comboBoxCourse = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getCoursesList");
			}
		};
		panel.add(comboBoxCourse, "4, 6, fill, default");

		comboBoxLecturer = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getLecturerListAll");
			}
		};
		panel.add(comboBoxLecturer, "6, 6, fill, default");

		comboBoxRoom = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getRoomList");
			}
		};
		panel.add(comboBoxRoom, "8, 6, fill, default");
		model = new DefaultTableModel(new Object[][] {}, new String[] {
				"Course", "Lecturer", "Room", "Is passed", "Result" }) {
			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { HedspiObject.class,
					HedspiObject.class, HedspiObject.class, Boolean.class,
					Double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");

		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmDeleteSelected = new JMenuItem("Delete selected");
		mntmDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indices = table.getSelectedRows();
				Arrays.sort(indices);
				for (int i = indices.length - 1; i >= 0; i--) {
					model.removeRow(indices[i]);
				}
			}
		});
		popupMenu.add(mntmDeleteSelected);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(model);

		JLabel lblRightClickTable = DefaultComponentFactory.getInstance()
				.createLabel("Right click table for options");
		lblRightClickTable.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC,
				12));
		add(lblRightClickTable, "2, 6");
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
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
			model.addRow(new Object[] { it.getCourse(), it.getLecturer(),
					it.getRoom(), it.isPassed(), it.getResult() });
	}
}