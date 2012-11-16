package org.hsm.view.student;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;
import org.hsm.model.hedspiObject.Student;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class StudentViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OtherInfoPane panelStudentOther;
	private ContactPane panelContact;
	private HedspiObject hedspiObject;
	private StudentStatisticPane studentStatisticPane;
	private JPanel panel_1;
	private JButton btnExportToHtml;

	/**
	 * Create the panel.
	 */
	public StudentViewPane() {
		super();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(137dlu;pref)"),
				ColumnSpec.decode("max(98dlu;pref):grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default"), }));

		panelContact = new ContactPane();
		add(panelContact, "2, 2, fill, fill");

		JPanel panel = new JPanel();
		add(panel, "3, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("max(78dlu;pref):grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		panelStudentOther = new OtherInfoPane();
		panel.add(panelStudentOther, "1, 2, fill, fill");

		panel_1 = new JPanel();
		panel.add(panel_1, "1, 4, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Commit student's information to server");
		panel_1.add(btnSave, "1, 1, center, default");

		btnExportToHtml = new JButton("Export to html");
		btnExportToHtml
				.setToolTipText("Export student's information to html file");
		btnExportToHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HedspiTable hedspiTable = getHedspiTable();
				if (hedspiTable == null)
					return;
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		panel_1.add(btnExportToHtml, "3, 1, center, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				Student st = new Student(hedspiObject.getId(), panelContact
						.getFirst(), panelContact.getLast(), panelContact
						.isMale(), panelContact.getDob(), panelContact
						.getEmails(), panelContact.getPhones(), panelContact
						.getNote(), panelContact.getHome(), panelContact
						.getDistrict(), panelStudentOther.getPoint(),
						panelStudentOther.getMssv(), panelStudentOther
								.getYear(), panelStudentOther.getHedspiClass());

				String message = (String) Control.getInstance().getData(
						"updateStudent", hedspiObject, st);
				if (message == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save student successful",
							"Save success", JOptionPane.INFORMATION_MESSAGE);
					hedspiObject.setName(st.getName());
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save student failed\nMessage: "
							+ message, "Save failed",
							JOptionPane.WARNING_MESSAGE);
			}
		});

		studentStatisticPane = new StudentStatisticPane();
		studentStatisticPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(studentStatisticPane, "1, 6, fill, fill");

	}

	public void setHedspiObject(HedspiObject hedspiObject) {
		if (hedspiObject == null)
			return;

		this.hedspiObject = hedspiObject;
		studentStatisticPane.setStudent(hedspiObject);
		Student student = (Student) Control.getInstance().getData(
				"getFullStudentData", hedspiObject);
		if (student != null) {
			panelContact.setContact(student);
			panelStudentOther.setStudent(student);
		} else
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Failed to get student info\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);

	}

	public HedspiTable getHedspiTable() {
		if (hedspiObject == null)
			return null;

		HedspiTable hedspiTable = new HedspiTable("Information of student {"
				+ hedspiObject.getName() + "}", "Label", "value");
		hedspiTable.setIsTablePrint(false);
		panelContact.export(hedspiTable);
		panelStudentOther.export(hedspiTable);
		studentStatisticPane.export(hedspiTable);

		return hedspiTable;
	}
}
