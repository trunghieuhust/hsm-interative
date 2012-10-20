package org.hsm.view.course;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.HedspiComboBox;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class DependencesListEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiComboBox comboBoxChooseCourse;
	private HedspiObject course;
	
	/**
	 * Create the panel.
	 */
	public DependencesListEditor() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		comboBoxChooseCourse = new HedspiComboBox(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				if (course == null)
					return new HedspiObject[0];
				return (HedspiObject[]) Control.getInstance().getData("getAddableBackgroundCourse", course);
			}};
		add(comboBoxChooseCourse, "2, 2, fill, default");
		
		JButton buttonAdd = new JButton("+");
		add(buttonAdd, "4, 2");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");
		
		JList<HedspiObject> listCurrentCourses = new JList<>();
		scrollPane.setViewportView(listCurrentCourses);
		
		JPanel panel = new JPanel();
		add(panel, "4, 4, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("39px"),},
			new RowSpec[] {
				RowSpec.decode("78px"),
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton buttonRemove = new JButton("-");
		panel.add(buttonRemove, "1, 2, left, top");
		
		JButton buttonReload = new JButton("R");
		panel.add(buttonReload, "1, 4");
	}

	public void setCourse(Course course) {
		this.course = course;
		comboBoxChooseCourse.refresh();
		// TODO Auto-generated method stub
		
	}

}
