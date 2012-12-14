package org.hsm.view.course;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.Course;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CourseViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldName;
	private JTextField textFieldCode;
	private JEditorPane editorPaneTopic;
	private JEditorPane editorPaneNote;
	private HedspiObject hedspiObject;
	private SpinnerNumberModel modelNFees;
	private SpinnerNumberModel modelNCredits;
	private SpinnerNumberModel modelTime;
	private DependencesListEditor dependencesListEditor;

	/**
	 * Create the panel.
	 */
	public CourseViewPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(174dlu;default)"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow(3)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		modelNFees = new SpinnerNumberModel(new Double(2), new Double(0), null,
				new Double(1));
		modelNCredits = new SpinnerNumberModel(new Integer(2), new Integer(0),
				null, new Integer(1));
		modelTime = new SpinnerNumberModel(new Double(45), new Double(0), null,
				new Double(5));

		JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
				"Name");
		add(lblName, "1, 2");
		lblName.setLabelFor(textFieldName);

		textFieldName = new JTextField();
		add(textFieldName, "3, 2");
		textFieldName.setToolTipText("Name of course");
		textFieldName.setColumns(10);

		JLabel lblCode = DefaultComponentFactory.getInstance().createLabel(
				"Code");
		add(lblCode, "1, 4");
		lblCode.setLabelFor(textFieldCode);

		textFieldCode = new JTextField();
		add(textFieldCode, "3, 4");
		textFieldCode.setToolTipText("Course's code");
		textFieldCode.setColumns(10);

		JLabel lblNumberOfFees = DefaultComponentFactory.getInstance()
				.createLabel("Number of fees");
		add(lblNumberOfFees, "1, 6");

		JSpinner spinnerNFees = new JSpinner();
		lblNumberOfFees.setLabelFor(spinnerNFees);
		add(spinnerNFees, "3, 6, fill, default");
		spinnerNFees.setToolTipText("Number of fee credits for course");
		spinnerNFees.setModel(modelNFees);

		JLabel lblNumberOfCredits = DefaultComponentFactory.getInstance()
				.createLabel("Number of credits");
		add(lblNumberOfCredits, "1, 8");

		JSpinner spinnerNCredits = new JSpinner();
		lblNumberOfCredits.setLabelFor(spinnerNCredits);
		add(spinnerNCredits, "3, 8, fill, default");
		spinnerNCredits.setToolTipText("Number of academic course");
		spinnerNCredits.setModel(modelNCredits);

		JLabel lblTime = DefaultComponentFactory.getInstance().createLabel(
				"Time");
		add(lblTime, "1, 10");

		JSpinner spinnerTime = new JSpinner();
		lblTime.setLabelFor(spinnerTime);
		add(spinnerTime, "3, 10, fill, default");
		spinnerTime.setToolTipText("Number of time credits for course");
		spinnerTime.setModel(modelTime);

		JLabel lblTopic = DefaultComponentFactory.getInstance().createLabel(
				"Topic");
		add(lblTopic, "1, 12");
		lblTopic.setLabelFor(editorPaneTopic);

		editorPaneTopic = new JEditorPane();
		editorPaneTopic.setPreferredSize(new Dimension(10, 10));
		add(editorPaneTopic, "3, 12");
		editorPaneTopic.setToolTipText("Topic of course");
		editorPaneTopic.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblDependanciesList = DefaultComponentFactory.getInstance()
				.createLabel("Dependancies list");
		add(lblDependanciesList, "1, 14");
		lblDependanciesList.setLabelFor(dependencesListEditor);

		dependencesListEditor = new DependencesListEditor();
		dependencesListEditor.setPreferredSize(new Dimension(10, 10));
		dependencesListEditor.setMinimumSize(new Dimension(10, 10));
		add(dependencesListEditor, "3, 14, fill, fill");

		JLabel lblNote = DefaultComponentFactory.getInstance().createLabel(
				"Note");
		add(lblNote, "1, 16");
		lblNote.setLabelFor(editorPaneNote);

		editorPaneNote = new JEditorPane();
		editorPaneNote.setPreferredSize(new Dimension(10, 10));
		add(editorPaneNote, "3, 16, default, fill");
		editorPaneNote.setToolTipText("Note for course");
		editorPaneNote.setBorder(new LineBorder(new Color(0, 0, 0)));

		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic('s');
		add(btnSave, "1, 18, center, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hedspiObject == null)
					return;
				Course cou = new Course(hedspiObject.getId(), textFieldName
						.getText(), modelNFees.getNumber().doubleValue(),
						modelNCredits.getNumber().intValue(), editorPaneTopic
								.getText(),
						modelTime.getNumber().doubleValue(), editorPaneNote
								.getText(), textFieldCode.getText());
				String message = (String) Control.getInstance().getData(
						"saveCourse", hedspiObject, cou,
						dependencesListEditor.getValues());
				if (message == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save course ok", "Save ok",
							JOptionPane.INFORMATION_MESSAGE);
					hedspiObject.setName(cou.getName());
				} else
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Save course failed\nMessage: "
							+ message, "Save failed",
							JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	private void setInfo(Course course) {
		if (course == null)
			return;
		textFieldCode.setText(course.getCode());
		textFieldName.setText(course.getName());
		editorPaneNote.setText(course.getNote());
		editorPaneTopic.setText(course.getTopic());
		modelTime.setValue(course.getTime());
		modelNCredits.setValue(course.getNCredits());
		modelNFees.setValue(course.getNFees());
		dependencesListEditor.setCourse(course);
	}

	public void setHedspiObject(HedspiObject hedspiObject) {
		this.hedspiObject = hedspiObject;
		Course course = (Course) Control.getInstance().getData(
				"getFullDataCourse", hedspiObject);
		if (course == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get information of course failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Load data failed", JOptionPane.WARNING_MESSAGE);
		} else {
			setInfo(course);

		}
	}

	public void export(HedspiTable hedspiTable) {
		hedspiTable.addValue("Name", textFieldName.getText());
		hedspiTable.addValue("Course code", textFieldCode.getText());
		hedspiTable.addValue("Number of fee credits",
				String.valueOf(modelNFees.getNumber().doubleValue()));
		hedspiTable.addValue("Number of credits",
				String.valueOf(modelNCredits.getNumber().doubleValue()));
		hedspiTable.addValue("Times (h)",
				String.valueOf(modelTime.getNumber().doubleValue()));
		hedspiTable.addValue("Topic", editorPaneTopic.getText());
		hedspiTable.addValue("Number of dependencies",
				String.valueOf(dependencesListEditor.getNDependencies()));
		hedspiTable.addValue("Note", editorPaneNote.getText());
	}
}
