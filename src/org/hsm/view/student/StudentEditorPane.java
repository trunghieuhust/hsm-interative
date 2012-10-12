package org.hsm.view.student;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;

import org.hsm.model.hedspiObject.Student;



public class StudentEditorPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentOtherPane panelStudentOther;
	private ContactPane panelContact;

	/**
	 * Create the panel.
	 */
	public StudentEditorPane() {
		super();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("109dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("98dlu:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		panelContact = new ContactPane();
		add(panelContact, "2, 2, fill, fill");
		
		JPanel panel = new JPanel();
		add(panel, "4, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		panelStudentOther = new StudentOtherPane();
		panel.add(panelStudentOther, "2, 2, fill, fill");
		
		JPanel panelButton = new JPanel();
		panel.add(panelButton, "2, 3, center, fill");
		panelButton.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnSave = new JButton("Save");
		panelButton.add(btnSave, "2, 2");
		
		JButton btnReset = new JButton("Reset");
		panelButton.add(btnReset, "4, 2");
		
	}

	public void setStudent(Student student) {
		panelContact.setContact(student);
		panelStudentOther.setStudent(student);
	}
}
