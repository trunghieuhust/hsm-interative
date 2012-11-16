package org.hsm.view.lecturer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.view.student.ObjectListPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FacultyViewPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LecturerViewPane lecturerViewPane;
	private HedspiObject faculty;
	protected JTextField textFieldFacultyName;
	private ObjectListPane lecturerListPane;
	private LecturerClassesListPane lecturerClassesListPane;

	/**
	 * Create the panel.
	 */
	public FacultyViewPane() {
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save faculty name");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (faculty == null)
					return;
				String name = textFieldFacultyName.getText();
				String message = (String) Control.getInstance().getData(
						"renameFaculty", faculty, name);
				if (message == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save faculty's name success",
							"Save success", JOptionPane.INFORMATION_MESSAGE);
					faculty.setName(name);
				} else {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Rename failed\nMessage: "
							+ message, "Rename failed",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnSave, "2, 2");

		textFieldFacultyName = new JTextField();
		textFieldFacultyName.setToolTipText("Name of faculty");
		panel_1.add(textFieldFacultyName, "4, 2, fill, default");
		textFieldFacultyName.setColumns(10);

		lecturerListPane = new ObjectListPane("Lecturers list") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				lecturerViewPane.setHedspiObject(value);
				lecturerClassesListPane.setHedspiObject(value);
			}

			@Override
			public HedspiObject newElement() {
				if (faculty == null)
					return null;
				return (HedspiObject) Control.getInstance().getData(
						"newLecturer", faculty);
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeLecturer",
						value);
			}

			@Override
			public HedspiObject[] getRefresh() {
				if (faculty == null)
					return new HedspiObject[0];
				return (HedspiObject[]) Control.getInstance().getData(
						"getLecturersListInFaculty", faculty);
			}

			@Override
			public String getTitle() {
				if (faculty == null)
					return null;
				return "Lecturers list in faculty {" + faculty.getName() + "}";
			}
		};
		lecturerListPane.setToolTipText("List of lecturers in faculty");
		panel.add(lecturerListPane, "2, 4, fill, fill");

		setLeftComponent(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setRightComponent(tabbedPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Contact information", null, scrollPane_1,
				"Contact information of lecturer");

		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Classes list", null, scrollPane_2,
				"List of classes that lecturer teaches in");

		lecturerClassesListPane = new LecturerClassesListPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			LecturerViewPane getLecturerViewPane() {
				return lecturerViewPane;
			}
		};
		scrollPane_2.setViewportView(lecturerClassesListPane);

		lecturerViewPane = new LecturerViewPane();
		scrollPane_1.setViewportView(lecturerViewPane);
		setDividerLocation(150);

	}

	public void setFaculty(HedspiObject value) {
		faculty = value;
		if (value != null) {
			textFieldFacultyName.setText(value.getName());
			lecturerListPane.refresh();
		}
	}
}
