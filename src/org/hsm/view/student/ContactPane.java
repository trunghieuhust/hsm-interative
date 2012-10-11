package org.hsm.view.student;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

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

	/**
	 * Create the panel.
	 */
	public ContactPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(39dlu;default):grow"),}));
		
		JLabel lblFirst = DefaultComponentFactory.getInstance().createLabel("First name");
		add(lblFirst, "2, 2, right, default");
		
		textFieldFirst = new JTextField();
		add(textFieldFirst, "4, 2, fill, default");
		textFieldFirst.setColumns(10);
		
		JLabel lblLastName = DefaultComponentFactory.getInstance().createLabel("Last name");
		add(lblLastName, "2, 4, right, default");
		
		textFieldLast = new JTextField();
		add(textFieldLast, "4, 4, fill, default");
		textFieldLast.setColumns(10);
		
		JLabel lblIsMale = DefaultComponentFactory.getInstance().createLabel("Is male");
		add(lblIsMale, "2, 6");
		
		JCheckBox checkBoxIsMale = new JCheckBox("");
		checkBoxIsMale.setSelected(true);
		add(checkBoxIsMale, "4, 6");
		
		JLabel lblBirthday = DefaultComponentFactory.getInstance().createLabel("Birthday");
		add(lblBirthday, "2, 8");
		
		JSpinner spinnerBirthday = new JSpinner();
		spinnerBirthday.setModel(new SpinnerDateModel(new Date(1349974800000L), null, null, Calendar.DAY_OF_YEAR));
		add(spinnerBirthday, "4, 8");
		
		JLabel lblCity = DefaultComponentFactory.getInstance().createLabel("City");
		add(lblCity, "2, 10");
		
		JComboBox<HedspiObject> comboBoxCity = new JComboBox<>();
		add(comboBoxCity, "4, 10, fill, default");
		
		JLabel lblDistrict = DefaultComponentFactory.getInstance().createLabel("District");
		add(lblDistrict, "2, 12");
		
		JComboBox<HedspiObject> comboBoxDistrict = new JComboBox<>();
		add(comboBoxDistrict, "4, 12, fill, default");
		
		JLabel lblHome = DefaultComponentFactory.getInstance().createLabel("Home");
		add(lblHome, "2, 14");
		
		textFieldHome = new JTextField();
		add(textFieldHome, "4, 14, fill, default");
		textFieldHome.setColumns(10);
		
		JLabel lblEmails = DefaultComponentFactory.getInstance().createLabel("Emails");
		add(lblEmails, "2, 16");
		
		QuickListEditor panelEmails = new QuickListEditor();
		add(panelEmails, "4, 16, fill, fill");
		
		JLabel lblPhones = DefaultComponentFactory.getInstance().createLabel("Phones");
		add(lblPhones, "2, 18");
		
		QuickListEditor panelPhones = new QuickListEditor();
		add(panelPhones, "4, 18, fill, fill");
		
		JLabel lblNote = DefaultComponentFactory.getInstance().createLabel("Note");
		add(lblNote, "2, 20");
		
		JEditorPane editorPaneNote = new JEditorPane();
		editorPaneNote.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(editorPaneNote, "4, 20, fill, fill");
	}
}
