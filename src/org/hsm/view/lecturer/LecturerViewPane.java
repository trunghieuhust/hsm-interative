package org.hsm.view.lecturer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hsm.control.Control;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LecturerViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContactPane contactPane;
	private HedspiComboBox comboBoxDegree;
	private HedspiComboBox comboBoxFaculty;
	private HedspiObject lecturer;

	/**
	 * Create the panel.
	 */
	public LecturerViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("134dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		contactPane = new ContactPane();
		add(contactPane, "2, 2, fill, fill");

		JPanel panel = new JPanel();
		add(panel, "4, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("236px:grow"),},
			new RowSpec[] {
				RowSpec.decode("70px"),
				FormFactory.DEFAULT_ROWSPEC,}));

		JPanel otherPane = new JPanel();
		panel.add(otherPane, "1, 1, fill, top");
		otherPane
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblFaculty = DefaultComponentFactory.getInstance().createLabel(
				"Faculty");
		otherPane.add(lblFaculty, "2, 2, right, default");

		comboBoxFaculty = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getListOfFaculties");
			}
		};
		otherPane.add(comboBoxFaculty, "4, 2, fill, default");

		JLabel lblDegree = DefaultComponentFactory.getInstance().createLabel(
				"Degree");
		otherPane.add(lblDegree, "2, 4, right, default");

		comboBoxDegree = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getListOfDegrees");
			}
		};
		otherPane.add(comboBoxDegree, "4, 4, fill, default");

		JButton btnSave = new JButton("Save");
		panel.add(btnSave, "1, 2, center, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lecturer == null)
					return;
				Lecturer newlt = new Lecturer(lecturer.getId(), contactPane
						.getFirst(), contactPane.getLast(), contactPane
						.isMale(), contactPane.getDob(), contactPane
						.getEmails(), contactPane.getPhones(), contactPane
						.getNote(), contactPane.getHome(), contactPane
						.getDistrict(), comboBoxDegree.getSelectedValue(),
						comboBoxFaculty.getSelectedValue());
				String message = (String) Control.getInstance().getData(
						"updateLecturer", lecturer, newlt);
				if (message == null) {
					lecturer.setName(newlt.getName());
					JOptionPane.showMessageDialog(null,
							"Update lecturer's information success",
							"Update success", JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null,
							"Update lecturer's information failed\nMessage: "
									+ message, "Update failed",
							JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	public void setLecturer(Lecturer lecturer) {
		if (lecturer != null) {
			this.lecturer = lecturer;
			contactPane.setContact(lecturer);
			comboBoxDegree.setSelectedValue(lecturer.getDegree());
			comboBoxFaculty.setSelectedValue(lecturer.getFaculty());
		}
	}

}
