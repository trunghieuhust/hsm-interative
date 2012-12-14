package org.hsm.view.student;

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

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ClassViewPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HedspiObject hedspiClass;

	private ObjectListPane studentListPane;
	private JTextField textFieldClassName;

	private StudentViewPane studentViewPane;

	private RegisterPane registerPane;

	/**
	 * Create the panel.
	 */
	public ClassViewPane() {

		JScrollPane scrollPane = new JScrollPane();
		setLeftComponent(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
				JButton btnSave = new JButton("Save");
				panel.add(btnSave, "2, 2");
				btnSave.setMnemonic('v');
				btnSave.setToolTipText("Save class's name");
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = textFieldClassName.getText();
						String message;
						if (hedspiClass != null) {
							message = (String) Control.getInstance().getData(
									"renameClass", hedspiClass, name);
							if (message != null)
								JOptionPane.showMessageDialog(Control.getInstance()
										.getMainWindow(),
										"Rename class name failed.\nMessage: "
												+ message, "Rename failed",
										JOptionPane.WARNING_MESSAGE);
							else {
								JOptionPane.showMessageDialog(Control.getInstance()
										.getMainWindow(), "Rename class ok",
										"Rename ok", JOptionPane.INFORMATION_MESSAGE);
								hedspiClass.setName(name);
							}
						}
					}
				});
		
				textFieldClassName = new JTextField();
				panel.add(textFieldClassName, "4, 2");
				textFieldClassName.setToolTipText("Name of class");
				textFieldClassName.setColumns(10);

		studentListPane = new ObjectListPane("Students list") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectValue(HedspiObject value) {
				studentViewPane.setHedspiObject(value);
				registerPane.setHedpiObject(value);
			}

			@Override
			public String removeElement(HedspiObject value) {
				return (String) Control.getInstance().getData("removeStudent",
						value);
			}

			@Override
			public HedspiObject newElement() {
				return (HedspiObject) (Control.getInstance().getData(
						"getNewRawStudentInClass", hedspiClass));
			}

			@Override
			public HedspiObject[] getRefresh() {
				if (hedspiClass == null)
					return new HedspiObject[0];
				return (HedspiObject[]) Control.getInstance().getData(
						"getStudentRawListInClass", hedspiClass);
			}

			@Override
			public String getTitle() {
				if (hedspiClass == null)
					return null;
				return "Students list of class {" + hedspiClass.getName() + "}";
			}
		};
		studentListPane.setToolTipText("List of classes");
		panel.add(studentListPane, "2, 4, 3, 1, fill, fill");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setRightComponent(tabbedPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Contact information", null, scrollPane_1, null);

		studentViewPane = new StudentViewPane();
		scrollPane_1.setViewportView(studentViewPane);

		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Academic information", null, scrollPane_2, null);

		registerPane = new RegisterPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			StudentViewPane getStudentViewPane() {
				return studentViewPane;
			}
		};
		scrollPane_2.setViewportView(registerPane);

		setDividerLocation(150);
	}

	public void setHedspiClass(HedspiObject hedspiClass) {
		this.hedspiClass = hedspiClass;
		studentListPane.refresh();
		textFieldClassName.setText(hedspiClass.getName());
	}
}
