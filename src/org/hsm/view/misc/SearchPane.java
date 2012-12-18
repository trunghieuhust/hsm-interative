package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hsm.util.StringUtil;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SearchPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<SEARCH_TYPE> comboBox;
	private JTextField txtObjectName;
	private QueryPane queryPane;
	private JTextField textFieldConverted;
	private JComboBox<SEARCH_METHOD> comboBox_1;
	private JSpinner spinnerChange;
	private JSpinner spinnerAdd;
	private JSpinner spinnerRemove;
	private SpinnerNumberModel modelLimit;
	private SpinnerNumberModel modelChange;
	private SpinnerNumberModel modelAdd;
	private SpinnerNumberModel modelRemove;

	private static enum SEARCH_TYPE {
		STUDENT, LECTURER, DISTRICT
	};

	private static enum SEARCH_METHOD {
		EXACT, CONTAIN, LEVENSTEIN, SOUNDEX
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

		comboBox = new JComboBox<SEARCH_TYPE>();
		lblSearchObject.setLabelFor(comboBox);
		panel.add(comboBox, "3, 1, fill, default");
		comboBox.setModel(new DefaultComboBoxModel<SEARCH_TYPE>(SEARCH_TYPE
				.values()));
		comboBox.setToolTipText("Choose object to serch for");

		JPanel panel_2 = new JPanel();
		add(panel_2, "2, 4, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblMethodology = DefaultComponentFactory.getInstance()
				.createLabel("Methodology");
		panel_2.add(lblMethodology, "1, 1, right, default");

		comboBox_1 = new JComboBox<SEARCH_METHOD>();
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean tmp = (SEARCH_METHOD) comboBox_1.getSelectedItem() == SEARCH_METHOD.LEVENSTEIN;
				spinnerRemove.setEnabled(tmp);
				spinnerAdd.setEnabled(tmp);
				spinnerChange.setEnabled(tmp);
			}
		});
		comboBox_1.setToolTipText("Select method to use");
		lblMethodology.setLabelFor(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel<SEARCH_METHOD>(
				SEARCH_METHOD.values()));
		comboBox_1.setSelectedIndex(0);
		panel_2.add(comboBox_1, "3, 1, fill, default");

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 6, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		txtObjectName = new JTextField();
		txtObjectName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textFieldConverted.setText(StringUtil.removeVNHat(
						txtObjectName.getText()).toLowerCase());
			}
		});
		txtObjectName.setToolTipText("Object name to search for");
		panel_1.add(txtObjectName, "1, 1, fill, default");
		txtObjectName.setColumns(10);

		textFieldConverted = new JTextField();
		textFieldConverted
				.setToolTipText("Converted text to transmiss to server");
		textFieldConverted.setEditable(false);
		panel_1.add(textFieldConverted, "3, 1, fill, default");
		textFieldConverted.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		panel_1.add(btnUpdate, "5, 1, left, default");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String relname;

				// Relation name
				SEARCH_TYPE searchType = (SEARCH_TYPE) comboBox
						.getSelectedItem();
				String fields;
				String queryField;
				String contactFields = "\n\tfirst AS \"First name\",\n"
						+ "\tlast AS \"Last name\",\n"
						+ "\tsex AS \"Is man?\",\n"
						+ "\tdob AS \"Date of birth\",\n"
						+ "\temails AS \"Emails\",\n"
						+ "\tphones AS \"Phones\",\n" + "\thome AS \"Home\",\n"
						+ "\tdistrict.name AS \"District\",\n"
						+ "\tcity.name AS \"City\",\n"
						+ "\tnote AS \"Note\",\n";
				String contactJoin = "\tJOIN district USING (dt)\n"
						+ "\tJOIN city USING (cy)";
				switch (searchType) {
				case STUDENT:
					relname = "student\n" + "\tJOIN class USING (cl)\n"
							+ contactJoin;
					fields = contactFields;
					fields += "\tpoint AS \"Enroll point\",\n"
							+ "\tclass.name AS \"Class\",\n"
							+ "\tmssv AS \"MSSV\",\n"
							+ "\tyear AS \"Enroll year\",\n"
							+ "\tk AS \"Student year\"";
					queryField = "student.name_no_hat";
					break;
				case LECTURER:
					relname = "lecturer\n" + contactJoin + "\n"
							+ "\tJOIN degree USING (dg)\n"
							+ "\tJOIN faculty USING (fc)";
					fields = contactFields;
					fields += "\tfaculty.name AS \"Faculty\",\n"
							+ "\tdegree.name AS \"Degree\"";
					queryField = "lecturer.name_no_hat";
					break;
				case DISTRICT:
					relname = "district JOIN city USING (cy)";
					fields = "\n\tdistrict.name AS \"Name\",\n"
							+ "\tcity.name AS \"City\"";
					queryField = "district.name_no_hat";
					break;
				default:
					return;
				}

				// Search method
				SEARCH_METHOD method = (SEARCH_METHOD) comboBox_1
						.getSelectedItem();
				String text = textFieldConverted.getText();
				text = text.replace("'", "''");

				String query;
				query = "SELECT " + fields + "\nFROM " + relname;

				query += "\n";
				switch (method) {
				case EXACT:
					query += "WHERE " + queryField + " = '" + text + "'";
					query += "\nORDER BY " + queryField;
					break;
				case CONTAIN:
					query += "WHERE " + queryField + " LIKE '%" + text + "%'";
					query += "\nORDER BY " + queryField;
					break;
				case LEVENSTEIN:
					query += "ORDER BY levenshtein(" + queryField + ", '"
							+ text + "', " + queryField + ", "
							+ modelAdd.getValue() + ", "
							+ modelRemove.getValue() + ","
							+ modelChange.getValue() + "), " + queryField;
					break;
				case SOUNDEX:
					query += "ORDER BY difference(" + queryField + ", '" + text
							+ "') DESC, " + queryField;
					break;
				default:
					return;
				}

				query += "\nLIMIT " + modelLimit.getValue();

				queryPane.setQuery(query);
			}
		});
		btnUpdate.setToolTipText("Update search query below");
		btnUpdate.setMnemonic('u');

		JLabel lblOptions = DefaultComponentFactory.getInstance().createLabel(
				"Options");
		add(lblOptions, "2, 8");

		JPanel panel_3 = new JPanel();
		add(panel_3, "2, 10, fill, fill");
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblLimit = DefaultComponentFactory.getInstance().createLabel(
				"Limit");
		panel_3.add(lblLimit, "1, 1, right, default");

		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("Result limit");
		lblLimit.setLabelFor(spinner);
		modelLimit = new SpinnerNumberModel(new Integer(10), new Integer(1),
				null, new Integer(10));
		spinner.setModel(modelLimit);
		panel_3.add(spinner, "3, 1");

		JPanel panel_4 = new JPanel();
		add(panel_4, "2, 12, fill, fill");
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblChange = DefaultComponentFactory.getInstance().createLabel(
				"Change");
		panel_4.add(lblChange, "1, 1");

		spinnerChange = new JSpinner();
		spinnerChange.setEnabled(false);
		spinnerChange.setToolTipText("Levenstein method - changing cost");
		lblChange.setLabelFor(spinnerChange);
		modelChange = new SpinnerNumberModel(new Integer(1), null, null,
				new Integer(1));
		spinnerChange.setModel(modelChange);
		panel_4.add(spinnerChange, "3, 1");

		JLabel lblAdd = DefaultComponentFactory.getInstance()
				.createLabel("Add");
		panel_4.add(lblAdd, "5, 1");

		spinnerAdd = new JSpinner();
		spinnerAdd.setEnabled(false);
		spinnerAdd.setToolTipText("Levenstein method - adding cost");
		lblAdd.setLabelFor(spinnerAdd);
		modelAdd = new SpinnerNumberModel(new Integer(1), null, null,
				new Integer(1));
		spinnerAdd.setModel(modelAdd);
		panel_4.add(spinnerAdd, "7, 1");

		JLabel lblRemove = DefaultComponentFactory.getInstance().createLabel(
				"Remove");
		panel_4.add(lblRemove, "9, 1");

		spinnerRemove = new JSpinner();
		spinnerRemove.setEnabled(false);
		spinnerRemove.setToolTipText("Levenstein method - remove cost");
		lblRemove.setLabelFor(spinnerRemove);
		modelRemove = new SpinnerNumberModel(new Integer(1), null, null,
				new Integer(1));
		spinnerRemove.setModel(modelRemove);
		panel_4.add(spinnerRemove, "11, 1");

		queryPane = new QueryPane();
		add(queryPane, "2, 14, fill, fill");
	}
}
