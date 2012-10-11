package org.hsm.view.student;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class QuickListEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public QuickListEditor() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		textField = new JTextField();
		add(textField, "2, 2, fill, default");
		textField.setColumns(10);
		
		JButton button = new JButton("+");
		add(button, "4, 2");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");
		
		JList<String> list = new JList<>();
		scrollPane.setViewportView(list);
		
		JButton button_1 = new JButton("-");
		add(button_1, "4, 4");
	}

}
