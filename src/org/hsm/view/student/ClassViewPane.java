package org.hsm.view.student;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiClass;
import org.hsm.model.hedspiObject.HedspiObject;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ClassViewPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HedspiClass hedspiClass;

	private ObjectListPane studentListPane;
	private JTextField textFieldClassName;

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
			void selectValue(HedspiObject value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			String removeElement(HedspiObject value) {
				return (String)Control.getInstance().getData("removeStudent", value);
			}
			
			@Override
			HedspiObject newElement() {
				return (HedspiObject)(Control.getInstance().getData("getNewRawStudentInClass", hedspiClass));
			}
			
			@Override
			HedspiObject[] getRefresh() {
				if (hedspiClass == null)
					return null;
				return (HedspiObject[])Control.getInstance().getData("getStudentRawListInClass", hedspiClass);
			}
		};
		panel.add(studentListPane, "2, 4, fill, fill");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		setRightComponent(scrollPane_1);
		
		StudentEditorPane studentEditorPane = new StudentEditorPane();
		studentEditorPane.setPreferredSize(new Dimension(430, 519));
		scrollPane_1.setViewportView(studentEditorPane);
		setDividerLocation(150);
	}

	public void setHedspiClass(HedspiClass hedspiClass) {
		this.hedspiClass = hedspiClass;
		studentListPane.refresh();
	}
}
