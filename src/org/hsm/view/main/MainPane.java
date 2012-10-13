package org.hsm.view.main;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.hsm.view.district.DistrictMainPane;
import org.hsm.view.student.StudentPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MainPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("38px:grow"),},
			new RowSpec[] {
				RowSpec.decode("7px:grow"),}));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		add(tabbedPane, "1, 1, fill, fill");
		
		StudentPane studentPanel = new StudentPane();
		tabbedPane.addTab("Student", null, studentPanel, null);
		
		DistrictMainPane districtMainPane = new DistrictMainPane();
		tabbedPane.addTab("Districts", null, districtMainPane, null);
	}
}
