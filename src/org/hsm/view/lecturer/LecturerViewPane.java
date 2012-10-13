package org.hsm.view.lecturer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Lecturer;
import org.hsm.view.student.ContactPane;
import org.hsm.view.student.HedspiComboBox;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;

public class LecturerViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContactPane contactPane;
	private JPanel otherPane;

	/**
	 * Create the panel.
	 */
	public LecturerViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("134dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		contactPane = new ContactPane();
		add(contactPane, "2, 2, fill, fill");
		
		JPanel panel = new JPanel();
		add(panel, "4, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("236px:grow"),},
			new RowSpec[] {
				RowSpec.decode("70px"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		otherPane = new JPanel();
		panel.add(otherPane, "1, 1, fill, top");
		otherPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFaculty = DefaultComponentFactory.getInstance().createLabel("Faculty");
		otherPane.add(lblFaculty, "2, 2, right, default");
		
		HedspiComboBox comboBoxFaculty = new HedspiComboBox() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public
			HedspiObject[] getValues() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		otherPane.add(comboBoxFaculty, "4, 2, fill, default");
		
		JLabel lblDegree = DefaultComponentFactory.getInstance().createLabel("Degree");
		otherPane.add(lblDegree, "2, 4, right, default");
		
		HedspiComboBox comboBoxDegree = new HedspiComboBox() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		otherPane.add(comboBoxDegree, "4, 4, fill, default");
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "1, 2, center, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnSave = new JButton("Save");
		panel_1.add(btnSave, "2, 2");
		
		JButton btnReset = new JButton("Reset");
		panel_1.add(btnReset, "4, 2");
		
		JButton btnReload = new JButton("Reload");
		panel_1.add(btnReload, "6, 2");
	}

	public void setLecturer(Lecturer lecturer) {
		if (lecturer != null){
			contactPane.setContact(lecturer);
		}
		// TODO Auto-generated method stub
		
	}

}
