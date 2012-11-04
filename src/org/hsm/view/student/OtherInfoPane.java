package org.hsm.view.student;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;
import org.hsm.model.hedspiObject.Student;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class OtherInfoPane extends JPanel {

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
	public OtherInfoPane() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("min:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblMssv = DefaultComponentFactory.getInstance().createLabel(
				"Student code");
		add(lblMssv, "2, 2, right, default");

		textFieldMssv = new JTextField();
		textFieldMssv.setToolTipText("Student code");
		lblMssv.setLabelFor(textFieldMssv);
		add(textFieldMssv, "4, 2, fill, default");
		textFieldMssv.setColumns(10);

		JLabel lblClass = DefaultComponentFactory.getInstance().createLabel(
				"Class");
		add(lblClass, "2, 4, right, default");

		comboBoxClass = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getClassList");
			}
		};
		comboBoxClass
				.setToolTipText("Changing class will remove student from current class on next refresh");
		lblClass.setLabelFor(comboBoxClass);
		add(comboBoxClass, "4, 4, fill, default");

		JLabel lblPoint = DefaultComponentFactory.getInstance().createLabel(
				"Enroll point");
		add(lblPoint, "2, 6, right, default");

		JSpinner spinnerPoint = new JSpinner();
		spinnerPoint.setToolTipText("Point student archieve in enroll exam");
		lblPoint.setLabelFor(spinnerPoint);
		modelPoint = new SpinnerNumberModel(0.0, 0.0, 30.0, 1.0);
		spinnerPoint.setModel(new SpinnerNumberModel(0.0, 0.0, 30.0, 0.0));
		add(spinnerPoint, "4, 6");

		JLabel lblYear = DefaultComponentFactory.getInstance().createLabel(
				"Enroll year");
		add(lblYear, "2, 8, right, default");

		modelYear = new SpinnerNumberModel(new Integer(0), new Integer(0),
				null, new Integer(1));
		JSpinner spinnerYear = new JSpinner();
		spinnerYear.setToolTipText("Year that student enrolled");
		lblYear.setLabelFor(spinnerYear);
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

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Student code", textFieldMssv.getText());
		HedspiObject obj = comboBoxClass.getSelectedObject();
		hedspiTable.addValue("Class", obj == null ? "null" : obj.getName());
		hedspiTable.addValue("Enroll point", modelPoint.getValue().toString());
		hedspiTable.addValue("Enroll year", modelYear.getValue().toString());
	}
}
