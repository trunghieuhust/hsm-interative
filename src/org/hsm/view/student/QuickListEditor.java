package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class QuickListEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private DefaultListModel<String> listModel;
	private JList<String> list;

	/**
	 * Create the panel.
	 */
	public QuickListEditor() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		textField = new JTextField();
		add(textField, "2, 2, fill, default");
		textField.setColumns(10);

		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals(""))
					listModel.addElement(textField.getText());
				textField.setText("");
			}
		});
		add(buttonAdd, "4, 2");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");

		listModel = new DefaultListModel<>();
		list = new JList<>(listModel);
		scrollPane.setViewportView(list);

		JButton buttonRemove = new JButton("-");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] arr = list.getSelectedIndices();
				Arrays.sort(arr);
				for (int i = arr.length - 1; i >= 0; i--)
					listModel.removeElementAt(i);
			}
		});
		add(buttonRemove, "4, 4");
	}

	public void setValues(String[] values) {
		listModel.clear();
		for (String it : values)
			listModel.addElement(it);
	}

	public String[] getValues() {
		String[] ret = new String[listModel.getSize()];
		for (int i = 0; i < listModel.getSize(); i++)
			ret[i] = listModel.getElementAt(i);
		return ret;
	}

	public String toLineString() {
		String ret = "";
		for (int i = 0; i < listModel.getSize(); i++)
			ret += listModel.getElementAt(i) + "; ";
		return ret;
	}

}
