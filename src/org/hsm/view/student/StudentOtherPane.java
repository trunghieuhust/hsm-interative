package org.hsm.view.student;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.hsm.model.hedspiObject.HedspiObject;

public class StudentOtherPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldMssv;

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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblMssv = DefaultComponentFactory.getInstance().createLabel("Student code");
		add(lblMssv, "2, 2, right, default");
		
		textFieldMssv = new JTextField();
		add(textFieldMssv, "4, 2, fill, default");
		textFieldMssv.setColumns(10);
		
		JLabel lblClass = DefaultComponentFactory.getInstance().createLabel("Class");
		add(lblClass, "2, 4, right, default");
		
		JComboBox<HedspiObject> comboBoxClass = new JComboBox<>();
		add(comboBoxClass, "4, 4, fill, default");
		
		JLabel lblPoint = DefaultComponentFactory.getInstance().createLabel("Enroll point");
		add(lblPoint, "2, 6");
		
		JSpinner spinnerPoint = new JSpinner();
		spinnerPoint.setModel(new SpinnerNumberModel(0.0, 0.0, 30.0, 1.0));
		add(spinnerPoint, "4, 6");
		
		JLabel lblYear = DefaultComponentFactory.getInstance().createLabel("Enroll year");
		add(lblYear, "2, 8");
		
		JSpinner spinnerYear = new JSpinner();
		spinnerYear.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		add(spinnerYear, "4, 8");
	}
}