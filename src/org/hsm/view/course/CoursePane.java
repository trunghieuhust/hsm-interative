package org.hsm.view.course;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;
import org.hsm.view.course.visual.HierachyView;
import org.hsm.view.student.ObjectListPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CoursePane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CourseViewPane courseViewPane;
	private HierachyView hierachyView;
	private CourseStatisticPane courseStatisticPane;
	private HedspiObject hedspiObject;
	private JTable table;
	private DefaultTableModel dataModel;
	private JCheckBox chckbxIncludeClassesList;

	/**
	 * Create the panel.
	 */
	public CoursePane() {

		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);

		ObjectListPane courseListPane = new ObjectListPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				courseViewPane.setHedspiObject(value);
				hierachyView.setHedspiObject(value);
				courseStatisticPane.setCourse(value);
				hedspiObject = value;
				refreshClassesList();
			}

			@Override
			public String getTitle() {
				return "List of courses";
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeCourse",
						value);
			}

			@Override
			public HedspiObject newElement() {
				return (HedspiObject) Control.getInstance()
						.getData("newCourse");
			}

			@Override
			public HedspiObject[] getRefresh() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getCoursesList");
			}
		};
		courseListPane.setToolTipText("List of courses");
		scrollPane.setViewportView(courseListPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setRightComponent(tabbedPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setToolTipText("Basic information of hedspiObject");
		tabbedPane.addTab("Course's information", null, scrollPane_1, null);

		courseViewPane = new CourseViewPane();
		scrollPane_1.setViewportView(courseViewPane);

		hierachyView = new HierachyView();
		hierachyView.setToolTipText("Acyclic (tree) hierachy view");
		tabbedPane.addTab("Visual Hierachical tree", null, hierachyView, null);

		courseStatisticPane = new CourseStatisticPane();
		courseStatisticPane.setToolTipText("Some meaningful statistics");
		tabbedPane.addTab("Statistic", null, courseStatisticPane, null);

		JPanel panel = new JPanel();
		panel.setToolTipText("Export to html format");
		tabbedPane.addTab("Export", null, panel, null);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton button = new JButton("Refresh");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshClassesList();
			}
		});
		button.setToolTipText("Refresh classes list");
		button.setMnemonic('r');
		panel_1.add(button, "1, 1");

		JButton btnExport = new JButton("Export");
		btnExport.setMnemonic('x');
		panel_1.add(btnExport, "3, 1, left, default");

		chckbxIncludeClassesList = new JCheckBox("Include classes list");
		chckbxIncludeClassesList
				.setToolTipText("Print classes list as a table below export file");
		chckbxIncludeClassesList.setSelected(true);
		panel_1.add(chckbxIncludeClassesList, "5, 1");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of hedspiObject {"
								+ hedspiObject.getName() + "}", "label",
						"value");
				hedspiTable.setIsTablePrint(false);
				courseViewPane.export(hedspiTable);
				courseStatisticPane.export(hedspiTable);
				if (chckbxIncludeClassesList.isSelected()) {
					HedspiTable table = new HedspiTable(
							"Class instances list of course {"
									+ hedspiObject.getName() + "}", dataModel);
					hedspiTable.writeToHtmlWithMessageDialog(table);
				} else
					hedspiTable.writeToHtmlWithMessageDialog();
			}
		});

		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, "2, 4, fill, fill");

		table = new JTable();
		dataModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"ID", "Lecturer", "Room" }) {
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
		table.setToolTipText("Class instaces list");
		scrollPane_2.setViewportView(table);
		setDividerLocation(150);
	}

	protected void refreshClassesList() {
		if (hedspiObject == null)
			return;
		String[] arr = (String[]) Control.getInstance().getData(
				"getClassesListOfCourse", hedspiObject);
		if (arr == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get classes list of course falied\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Refresh data failed", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int i = dataModel.getRowCount() - 1; i >= 0; i--)
			dataModel.removeRow(i);
		for (int i = 0; i + 2 < arr.length; i += 3) {
			dataModel.addRow(new Object[] { arr[i], arr[i + 1], arr[i + 2] });
		}
	}
}
