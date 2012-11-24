package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JCheckBox;

public class SearchPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<SEARCH_TYPE> comboBox;
	private JTextField txtObjectName;
	private QueryPane queryPane;
	private JCheckBox chckbxApproximate;

	private static enum SEARCH_TYPE {
		STUDENT, LECTURER, DISTRICT
	};

	/**
	 * Create the panel.
	 */
	public SearchPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblSearchObject = DefaultComponentFactory.getInstance()
				.createLabel("Search object");
		panel.add(lblSearchObject, "1, 1, right, default");
		lblSearchObject.setLabelFor(comboBox);

		comboBox = new JComboBox<SEARCH_TYPE>();
		panel.add(comboBox, "3, 1, fill, default");
		comboBox.setModel(new DefaultComboBoxModel<SEARCH_TYPE>(SEARCH_TYPE
				.values()));
		comboBox.setToolTipText("Choose object to serch for");

		chckbxApproximate = new JCheckBox("Approximate");
		add(chckbxApproximate, "2, 4");

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 6, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		txtObjectName = new JTextField();
		txtObjectName.setToolTipText("Object name to search for");
		panel_1.add(txtObjectName, "1, 1, fill, default");
		txtObjectName.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		panel_1.add(btnUpdate, "3, 1, left, default");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String relname;
				SEARCH_TYPE searchType = (SEARCH_TYPE) comboBox
						.getSelectedItem();
				switch (searchType) {
				case STUDENT:
					relname = "student";
					break;
				case LECTURER:
					relname = "lecturer";
					break;
				case DISTRICT:
					relname = "district";
					break;
				default:
					relname = null;
					break;
				}

				if (relname == null)
					return;
				String query;
				if (chckbxApproximate.isSelected())
					query = "SELECT * FROM " + relname
							+ " WHERE name_no_hat LIKE lower(remove_hat('%"
							+ txtObjectName.getText().replace("'", "") + "%'))";
				else
					query = "SELECT * FROM " + relname
							+ " WHERE name_no_hat = '"
							+ txtObjectName.getText().replace("'", "") + "'";
				queryPane.setQuery(query);
			}
		});
		btnUpdate.setToolTipText("Update search query below");
		btnUpdate.setMnemonic('u');

		queryPane = new QueryPane();
		add(queryPane, "2, 8, fill, fill");
	}
}
