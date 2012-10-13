package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.Student;

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

	private StudentEditorPane studentEditorPane;

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
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textFieldClassName.getText();
				String message;
				if (hedspiClass != null && !hedspiClass.getName().equals(name)){
					message = (String) Control.getInstance().getData("renameClass", hedspiClass, name);
					if (message != null)
						JOptionPane.showMessageDialog(null, "Rename class name failed.\nMessage: " + message, "Rename failed", JOptionPane.ERROR_MESSAGE);
					else
						hedspiClass.setName(name);
				}
			}
		});
		panel_1.add(btnSave, "2, 2");
		
		textFieldClassName = new JTextField();
		panel_1.add(textFieldClassName, "4, 2, fill, default");
		textFieldClassName.setColumns(10);
		

		studentListPane = new ObjectListPane("Students list") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public
			void selectValue(HedspiObject value) {
				Student student = (Student)Control.getInstance().getData("getFullStudentData", value);
				if (student != null)
					studentEditorPane.setStudent(student);
				else
					JOptionPane.showMessageDialog(null, "Failed to get student info", "Get data failed", JOptionPane.ERROR_MESSAGE);
			}
			
			@Override
			public
			String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeStudent", value);
			}
			
			@Override
			public
			HedspiObject newElement() {
				return (HedspiObject)(Control.getInstance().getData("getNewRawStudentInClass", hedspiClass));
			}
			
			@Override
			public
			HedspiObject[] getRefresh() {
				if (hedspiClass == null)
					return null;
				return (HedspiObject[])Control.getInstance().getData("getStudentRawListInClass", hedspiClass);
			}
		};
		panel.add(studentListPane, "2, 4, fill, fill");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);
		
		studentEditorPane = new StudentEditorPane();
		scrollPane_1.setViewportView(studentEditorPane);
		setDividerLocation(150);
	}

	public void setHedspiClass(HedspiObject hedspiClass) {
		this.hedspiClass = hedspiClass;
		studentListPane.refresh();
		textFieldClassName.setText(hedspiClass.getName());
	}
}
