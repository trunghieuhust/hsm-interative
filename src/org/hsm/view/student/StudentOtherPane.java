package org.hsm.view.student;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Student;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class StudentOtherPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldMssv;
	private HedspiComboBox comboBoxClass;
	private SpinnerNumberModel modelPoint;
	private SpinnerNumberModel modelYear;

	/**
	 * Create the panel.
	 */
	public StudentOtherPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblMssv = DefaultComponentFactory.getInstance().createLabel("Student code");
		add(lblMssv, "2, 2, right, default");
		
		textFieldMssv = new JTextField();
		add(textFieldMssv, "4, 2, fill, default");
		textFieldMssv.setColumns(10);
		
		JLabel lblClass = DefaultComponentFactory.getInstance().createLabel("Class");
		add(lblClass, "2, 4, right, default");
		
		comboBoxClass = new HedspiComboBox(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			HedspiObject[] getValues() {
				return (HedspiObject[])Control.getInstance().getData("getClassList");
			}};
		add(comboBoxClass, "4, 4, fill, default");
		
		JLabel lblPoint = DefaultComponentFactory.getInstance().createLabel("Enroll point");
		add(lblPoint, "2, 6");
		
		JSpinner spinnerPoint = new JSpinner();
		modelPoint = new SpinnerNumberModel(0.0, 0.0, 30.0, 1.0);
		spinnerPoint.setModel(modelPoint);
		add(spinnerPoint, "4, 6");
		
		JLabel lblYear = DefaultComponentFactory.getInstance().createLabel("Enroll year");
		add(lblYear, "2, 8");
		
		modelYear = new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1));
		JSpinner spinnerYear = new JSpinner();
		spinnerYear.setModel(modelYear);
		add(spinnerYear, "4, 8");
	}

	public void setStudent(Student student) {
		textFieldMssv.setText(student.getMssv());
		comboBoxClass.setSelectedValue(student.getHedspiClass());
		modelPoint.setValue(student.getPoint());
		modelYear.setValue(student.getYear());
	}

	public double getPoint() {
		return modelPoint.getNumber().doubleValue();
	}

	public String getMssv() {
		return textFieldMssv.getText();
	}

	public int getYear() {
		return modelYear.getNumber().intValue();
	}

	public int getHedspiClass() {
		return comboBoxClass.getSelectedValue();
	}
}
