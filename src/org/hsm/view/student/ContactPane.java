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
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
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
		add(textFieldFirst, "4, 2, fill, default");
		textFieldFirst.setColumns(10);

		JLabel lblLastName = DefaultComponentFactory.getInstance().createLabel(
				"Last name");
		add(lblLastName, "2, 4, right, default");

		textFieldLast = new JTextField();
		add(textFieldLast, "4, 4, fill, default");
		textFieldLast.setColumns(10);

		JLabel lblIsMale = DefaultComponentFactory.getInstance().createLabel(
				"Is male");
		add(lblIsMale, "2, 6");

		checkBoxIsMale = new JCheckBox("");
		checkBoxIsMale.setSelected(true);
		add(checkBoxIsMale, "4, 6");

		JLabel lblBirthday = DefaultComponentFactory.getInstance().createLabel(
				"Birthday");
		add(lblBirthday, "2, 8");

		JSpinner spinnerBirthday = new JSpinner();
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
		add(comboBoxDistrict, "4, 12, fill, default");

		JLabel lblHome = DefaultComponentFactory.getInstance().createLabel(
				"Home");
		add(lblHome, "2, 14");

		textFieldHome = new JTextField();
		add(textFieldHome, "4, 14, fill, default");
		textFieldHome.setColumns(10);

		JLabel lblEmails = DefaultComponentFactory.getInstance().createLabel(
				"Emails");
		add(lblEmails, "2, 16");

		panelEmails = new QuickListEditor();
		add(panelEmails, "4, 16, fill, fill");

		JLabel lblPhones = DefaultComponentFactory.getInstance().createLabel(
				"Phones");
		add(lblPhones, "2, 18");

		panelPhones = new QuickListEditor();
		add(panelPhones, "4, 18, fill, fill");

		JLabel lblNote = DefaultComponentFactory.getInstance().createLabel(
				"Note");
		add(lblNote, "2, 20");

		editorPaneNote = new JEditorPane();
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
		int city = (int) Control.getInstance().getData("getCityOfDistrict",
				district);
		if (city == -1)
			return;
		comboBoxCity.setSelectedValue(city);
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
}
