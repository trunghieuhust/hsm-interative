package org.hsm.view.student;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Contact;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ContactPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldFirst;
	private JTextField textFieldLast;
	private JTextField textFieldHome;
	private JCheckBox checkBoxIsMale;
	private SpinnerDateModel modelBirthday;
	private JEditorPane editorPaneNote;
	private QuickListEditor panelEmails;
	private QuickListEditor panelPhones;
	private HedspiComboBox comboBoxCity;
	private HedspiComboBox comboBoxDistrict;

	/**
	 * Create the panel.
	 */
	public ContactPane() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("right:default"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("min:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:max(39dlu;default):grow"), }));

		JLabel lblFirst = DefaultComponentFactory.getInstance().createLabel(
				"First name");
		add(lblFirst, "2, 2, right, default");

		textFieldFirst = new JTextField();
		lblFirst.setLabelFor(textFieldFirst);
		add(textFieldFirst, "4, 2, fill, default");
		textFieldFirst.setColumns(10);

		JLabel lblLastName = DefaultComponentFactory.getInstance().createLabel(
				"Last name");
		add(lblLastName, "2, 4, right, default");

		textFieldLast = new JTextField();
		lblLastName.setLabelFor(textFieldLast);
		add(textFieldLast, "4, 4, fill, default");
		textFieldLast.setColumns(10);

		JLabel lblIsMale = DefaultComponentFactory.getInstance().createLabel(
				"Is male");
		add(lblIsMale, "2, 6");

		checkBoxIsMale = new JCheckBox("");
		lblIsMale.setLabelFor(checkBoxIsMale);
		checkBoxIsMale.setSelected(true);
		add(checkBoxIsMale, "4, 6");

		JLabel lblBirthday = DefaultComponentFactory.getInstance().createLabel(
				"Birthday");
		add(lblBirthday, "2, 8");

		JSpinner spinnerBirthday = new JSpinner();
		lblBirthday.setLabelFor(spinnerBirthday);
		modelBirthday = new SpinnerDateModel(new Date(1349974800000L), null,
				null, Calendar.DAY_OF_YEAR);
		spinnerBirthday.setModel(modelBirthday);
		add(spinnerBirthday, "4, 8");

		JLabel lblCity = DefaultComponentFactory.getInstance().createLabel(
				"City");
		add(lblCity, "2, 10");

		comboBoxCity = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				return (HedspiObject[]) Control.getInstance().getData(
						"getCityList");
			}
		};
		lblCity.setLabelFor(comboBoxCity);
		comboBoxCity.getComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				comboBoxDistrict.refresh();
			}
		});
		add(comboBoxCity, "4, 10, fill, default");

		JLabel lblDistrict = DefaultComponentFactory.getInstance().createLabel(
				"District");
		add(lblDistrict, "2, 12");

		comboBoxDistrict = new HedspiComboBox() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public HedspiObject[] getValues() {
				int city = comboBoxCity.getSelectedValue();
				if (city == -1)
					return new HedspiObject[0];
				return (HedspiObject[]) Control.getInstance().getData(
						"getDistricsList", city);
			}
		};
		lblDistrict.setLabelFor(comboBoxDistrict);
		add(comboBoxDistrict, "4, 12, fill, default");

		JLabel lblHome = DefaultComponentFactory.getInstance().createLabel(
				"Home");
		add(lblHome, "2, 14");

		textFieldHome = new JTextField();
		lblHome.setLabelFor(textFieldHome);
		add(textFieldHome, "4, 14, fill, default");
		textFieldHome.setColumns(10);

		JLabel lblEmails = DefaultComponentFactory.getInstance().createLabel(
				"Emails");
		add(lblEmails, "2, 16");

		panelEmails = new QuickListEditor();
		lblEmails.setLabelFor(panelEmails);
		add(panelEmails, "4, 16, fill, fill");

		JLabel lblPhones = DefaultComponentFactory.getInstance().createLabel(
				"Phones");
		add(lblPhones, "2, 18");

		panelPhones = new QuickListEditor();
		lblPhones.setLabelFor(panelPhones);
		add(panelPhones, "4, 18, fill, fill");

		JLabel lblNote = DefaultComponentFactory.getInstance().createLabel(
				"Note");
		add(lblNote, "2, 20");

		editorPaneNote = new JEditorPane();
		lblNote.setLabelFor(editorPaneNote);
		editorPaneNote.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		add(editorPaneNote, "4, 20, fill, fill");
	}

	public void setContact(Contact contact) {
		textFieldFirst.setText(contact.getFirst());
		textFieldLast.setText(contact.getLast());
		checkBoxIsMale.setSelected(contact.isMale());
		modelBirthday.setValue(contact.getDob());
		updateDistrict(contact.getDistrict());
		textFieldHome.setText(contact.getHome());
		panelEmails.setValues(contact.getEmails());
		editorPaneNote.setText(contact.getNote());
	}

	private void updateDistrict(int district) {
		HedspiObject city = (HedspiObject) Control.getInstance().getData("getCityOfDistrictFromDistrictId",
				district);
		if (city == null)
			return;
		comboBoxCity.setSelectedValue(city.getId());
		comboBoxDistrict.setSelectedValue(district);
	}

	public String getFirst() {
		return textFieldFirst.getText();
	}

	public String getLast() {
		return textFieldLast.getText();
	}

	public boolean isMale() {
		return checkBoxIsMale.isSelected();
	}

	public Date getDob() {
		return modelBirthday.getDate();
	}

	public String[] getEmails() {
		return panelEmails.getValues();
	}

	public String[] getPhones() {
		return panelPhones.getValues();
	}

	public String getNote() {
		return editorPaneNote.getText();
	}

	public String getHome() {
		return textFieldHome.getText();
	}

	public int getDistrict() {
		return comboBoxDistrict.getSelectedValue();
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("First name", textFieldFirst.getText());
		hedspiTable.addValue("Last name", textFieldLast.getText());
		hedspiTable.addValue("Gender", checkBoxIsMale.isSelected() ? "Male"
				: "Female");
		hedspiTable.addValue("Birthday", modelBirthday.getDate().toString());

		HedspiObject obj = comboBoxCity.getSelectedObject();
		hedspiTable.addValue("City", obj == null ? "null" : obj.getName());
		obj = comboBoxDistrict.getSelectedObject();
		hedspiTable.addValue("District", obj == null ? "null" : obj.getName());

		hedspiTable.addValue("Home", textFieldHome.getText());
		hedspiTable.addValue("Emails", panelEmails.toLineString());
		hedspiTable.addValue("Phones", panelPhones.toLineString());
		hedspiTable.addValue("Note", editorPaneNote.getText());
	}
}
