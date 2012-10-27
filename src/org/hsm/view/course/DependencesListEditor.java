package org.hsm.view.course;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private DefaultListModel<HedspiObject> coursesModel;
	private JButton buttonReload;
	private JList<HedspiObject> listCurrentCourses;
	
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

		coursesModel = new DefaultListModel<HedspiObject>();
		comboBoxChooseCourse = new HedspiComboBox(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				if (course == null)
					return new HedspiObject[0];
				HedspiObject[] val = (HedspiObject[]) Control.getInstance().getData("getAddableBackgroundCourse", course);
				ArrayList<HedspiObject> ret = new ArrayList<>();
				for(HedspiObject it : val){
					boolean found = false;
					for(int i = 0; i < coursesModel.size(); i++)
						if (coursesModel.getElementAt(i).equals(it)){
							found = true;
							break;
						}
					if (!found)
						ret.add(it);
				}
				return ret.toArray(new HedspiObject[ret.size()]);
			}};
		add(comboBoxChooseCourse, "2, 2, fill, default");
		
		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HedspiObject selected = comboBoxChooseCourse.getSelectedObject();
				if (selected == null)
					return;
				comboBoxChooseCourse.removeObject(selected);
				coursesModel.addElement(selected);
			}
		});
		add(buttonAdd, "4, 2");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");
		
		listCurrentCourses = new JList<>(coursesModel);
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
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<HedspiObject> indices = listCurrentCourses.getSelectedValuesList();
				for(HedspiObject it : indices){
					coursesModel.removeElement(it);
					comboBoxChooseCourse.addObject(it);
				}
			}
		});
		panel.add(buttonRemove, "1, 2, left, top");
		
		buttonReload = new JButton("R");
		buttonReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (course == null)
					return;
				HedspiObject[] backgrounds = (HedspiObject[]) Control.getInstance().getData("getBackgroundCourses", course);
				if (backgrounds == null)
					JOptionPane.showMessageDialog(null, "Refresh background courses failed", "Refresh failed", JOptionPane.ERROR_MESSAGE);
				else{
					coursesModel.removeAllElements();
					for(HedspiObject it : backgrounds)
						coursesModel.addElement(it);
					comboBoxChooseCourse.refresh();
//					JOptionPane.showMessageDialog(null, "Refresh background courses ok", "Refresh ok", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel.add(buttonReload, "1, 4");
	}

	public void setCourse(Course course) {
		this.course = course;
		buttonReload.doClick();
	}

	public HedspiObject[] getValues() {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for(int i = 0; i < coursesModel.getSize(); i++)
			ret.add(coursesModel.getElementAt(i));
		return ret.toArray(new HedspiObject[ret.size()]);
	}

}
