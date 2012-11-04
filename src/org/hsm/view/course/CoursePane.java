package org.hsm.view.course;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

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
	private HedspiObject course;

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
				course = value;
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
		tabbedPane.addTab("Course's information", null, scrollPane_1, null);

		courseViewPane = new CourseViewPane();
		scrollPane_1.setViewportView(courseViewPane);

		hierachyView = new HierachyView();
		tabbedPane.addTab("Visual Hierachical tree", null, hierachyView, null);

		courseStatisticPane = new CourseStatisticPane();
		tabbedPane.addTab("Statistic", null, courseStatisticPane, null);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Export", null, panel, null);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (course == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of course {" + course.getName() + "}",
						"label", "value");
				hedspiTable.setIsTablePrint(false);
				courseViewPane.export(hedspiTable);
				courseStatisticPane.export(hedspiTable);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel.add(btnExport, "2, 2");
		setDividerLocation(150);
	}
}
