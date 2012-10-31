package org.hsm.view.main;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.hsm.view.course.CoursePane;
import org.hsm.view.district.DistrictPane;
import org.hsm.view.lecturer.LecturerPane;
import org.hsm.view.misc.QueryPane;
import org.hsm.view.room.RoomPane;
import org.hsm.view.student.StudentPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblStatus;

	/**
	 * Create the panel.
	 */
	public MainPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("38px:grow"),},
			new RowSpec[] {
				RowSpec.decode("7px:grow"),
				FormFactory.DEFAULT_ROWSPEC,}));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		add(tabbedPane, "1, 1, fill, fill");

		StudentPane studentPanel = new StudentPane();
		tabbedPane.addTab("Student", new ImageIcon("/home/haidang-ubuntu/git/hsm-interative/hsm-interative/icon/callcenter-girls-glasses.ico"), studentPanel, null);

		LecturerPane lecturerPanel = new LecturerPane();
		tabbedPane.addTab("Lecturer", null, lecturerPanel, null);

		DistrictPane districtPane = new DistrictPane();
		tabbedPane.addTab("District", null, districtPane, null);

		RoomPane roomPane = new RoomPane();
		tabbedPane.addTab("Room", null, roomPane, null);

		CoursePane coursePane = new CoursePane();
		tabbedPane.addTab("Course", null, coursePane, null);
		
		JTabbedPane miscPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Misc", null, miscPane, null);
		
		JTabbedPane imexPane = new JTabbedPane(JTabbedPane.TOP);
		miscPane.addTab("Im/Ex(port)", null, imexPane, null);
		
		JPanel importPane = new JPanel();
		imexPane.addTab("Import", null, importPane, null);
		
		JPanel exportPane = new JPanel();
		imexPane.addTab("Export", null, exportPane, null);
		
		JTabbedPane maintainPane = new JTabbedPane(JTabbedPane.TOP);
		miscPane.addTab("Maintain", null, maintainPane, null);
		
		JPanel backupPane = new JPanel();
		maintainPane.addTab("Backup", null, backupPane, null);
		
		JPanel restorePane = new JPanel();
		maintainPane.addTab("Restore", null, restorePane, null);
		
		JScrollPane queryScrollPane = new JScrollPane();
		miscPane.addTab("Query", null, queryScrollPane, null);
		
		QueryPane queryPane = new QueryPane();
		queryScrollPane.setViewportView(queryPane);
		
		JScrollPane batchDelScrollPane = new JScrollPane();
		miscPane.addTab("Batch delete", null, batchDelScrollPane, null);
		
		JPanel batchDelPane = new JPanel();
		batchDelScrollPane.setViewportView(batchDelPane);
		
		JScrollPane searchScrollPane = new JScrollPane();
		miscPane.addTab("Search", null, searchScrollPane, null);
		
		JPanel searchPane = new JPanel();
		searchScrollPane.setViewportView(searchPane);
		
		JScrollPane statisticScrollPane = new JScrollPane();
		miscPane.addTab("Statistic", null, statisticScrollPane, null);
		
		JPanel statisticPane = new JPanel();
		statisticScrollPane.setViewportView(statisticPane);
		
		JPanel panel = new JPanel();
		add(panel, "1, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblStatus = DefaultComponentFactory.getInstance().createLabel("Status");
		lblStatus.setFont(new Font("Dialog", Font.ITALIC, 12));
		panel.add(lblStatus, "4, 1");
	}
	
	public void setStatus(String status){
		lblStatus.setText(status);
	}
}
