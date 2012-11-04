package org.hsm.view.lecturer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;
import org.hsm.model.hedspiObject.Lecturer;
import org.hsm.view.student.ContactPane;
import org.hsm.view.student.HedspiComboBox;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class LecturerViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContactPane contactPane;
	private HedspiComboBox comboBoxDegree;
	private HedspiComboBox comboBoxFaculty;
	private HedspiObject hedspiObject;
	private LecturerStatisticPane lecturerStatisticPane;

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
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("236px:grow"), }, new RowSpec[] {
				RowSpec.decode("70px"), FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

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
		comboBoxFaculty
				.setToolTipText("Changing faculty will remove lecturer from current faculty on next refresh");
		lblFaculty.setLabelFor(comboBoxFaculty);
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
		comboBoxDegree.setToolTipText("Choose academic degree");
		lblDegree.setLabelFor(comboBoxDegree);
		otherPane.add(comboBoxDegree, "4, 4, fill, default");

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "1, 2, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("60px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save lecturer's information");
		panel_1.add(btnSave, "1, 1, right, default");

		JButton btnExport = new JButton("Export");
		btnExport.setToolTipText("Export lecturer's information to html file");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				HedspiTable hedspiTable = new HedspiTable(
						"Information of lecturer {" + hedspiObject.getName()
								+ "}", "Label", "value");
				hedspiTable.setIsTablePrint(false);
				contactPane.export(hedspiTable);
				lecturerStatisticPane.export(hedspiTable);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel_1.add(btnExport, "3, 1, left, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				Lecturer newlt = new Lecturer(hedspiObject.getId(), contactPane
						.getFirst(), contactPane.getLast(), contactPane
						.isMale(), contactPane.getDob(), contactPane
						.getEmails(), contactPane.getPhones(), contactPane
						.getNote(), contactPane.getHome(), contactPane
						.getDistrict(), comboBoxDegree.getSelectedValue(),
						comboBoxFaculty.getSelectedValue());
				String message = (String) Control.getInstance().getData(
						"updateLecturer", hedspiObject, newlt);
				if (message == null) {
					hedspiObject.setName(newlt.getName());
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Update lecturer's information success",
							"Update success", JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Update lecturer's information failed\nMessage: "
									+ message, "Update failed",
							JOptionPane.WARNING_MESSAGE);
			}
		});

		lecturerStatisticPane = new LecturerStatisticPane();
		lecturerStatisticPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(lecturerStatisticPane, "1, 4, fill, fill");
	}

	private void setInfo(Lecturer lecturer) {
		if (lecturer != null) {
			contactPane.setContact(lecturer);
			comboBoxDegree.setSelectedValue(lecturer.getDegree());
			comboBoxFaculty.setSelectedValue(lecturer.getFaculty());
		}
	}

	public void setHedspiObject(HedspiObject hedspiObject) {
		if (hedspiObject == null)
			return;

		lecturerStatisticPane.setLecturer(hedspiObject);
		this.hedspiObject = hedspiObject;
		Lecturer lecturer = (Lecturer) Control.getInstance().getData(
				"getFullDataLecturer", hedspiObject);
		if (lecturer != null) {
			setInfo(lecturer);
		} else
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get data of lecturer failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);
	}
}
