package org.hsm.view.course;

import java.awt.Color;
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
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
				"Name");
		panel.add(lblName, "2, 2");

		textFieldName = new JTextField();
		textFieldName.setToolTipText("Name of course");
		lblName.setLabelFor(textFieldName);
		panel.add(textFieldName, "4, 2, fill, default");
		textFieldName.setColumns(10);

		JLabel lblCode = DefaultComponentFactory.getInstance().createLabel(
				"Code");
		panel.add(lblCode, "2, 4");

		textFieldCode = new JTextField();
		textFieldCode.setToolTipText("Course's code");
		lblCode.setLabelFor(textFieldCode);
		panel.add(textFieldCode, "4, 4, fill, default");
		textFieldCode.setColumns(10);

		JLabel lblNumberOfFees = DefaultComponentFactory.getInstance()
				.createLabel("Number of fees");
		panel.add(lblNumberOfFees, "2, 6");

		JSpinner spinnerNFees = new JSpinner();
		spinnerNFees.setToolTipText("Number of fee credits for course");
		lblNumberOfFees.setLabelFor(spinnerNFees);
		modelNFees = new SpinnerNumberModel(new Double(2), new Double(0), null,
				new Double(1));
		spinnerNFees.setModel(modelNFees);
		panel.add(spinnerNFees, "4, 6");

		JLabel lblNumberOfCredits = DefaultComponentFactory.getInstance()
				.createLabel("Number of credits");
		panel.add(lblNumberOfCredits, "2, 8");

		JSpinner spinnerNCredits = new JSpinner();
		spinnerNCredits.setToolTipText("Number of academic course");
		lblNumberOfCredits.setLabelFor(spinnerNCredits);
		modelNCredits = new SpinnerNumberModel(new Integer(2), new Integer(0),
				null, new Integer(1));
		spinnerNCredits.setModel(modelNCredits);
		panel.add(spinnerNCredits, "4, 8");

		JLabel lblTime = DefaultComponentFactory.getInstance().createLabel(
				"Time");
		panel.add(lblTime, "2, 10");

		JSpinner spinnerTime = new JSpinner();
		spinnerTime.setToolTipText("Number of time credits for course");
		lblTime.setLabelFor(spinnerTime);
		modelTime = new SpinnerNumberModel(new Double(45), new Double(0), null,
				new Double(5));
		spinnerTime.setModel(modelTime);
		panel.add(spinnerTime, "4, 10");

		JLabel lblTopic = DefaultComponentFactory.getInstance().createLabel(
				"Topic");
		panel.add(lblTopic, "2, 12");

		editorPaneTopic = new JEditorPane();
		editorPaneTopic.setToolTipText("Topic of course");
		lblTopic.setLabelFor(editorPaneTopic);
		editorPaneTopic.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(editorPaneTopic, "4, 12, fill, fill");

		JLabel lblDependanciesList = DefaultComponentFactory.getInstance()
				.createLabel("Dependancies list");
		panel.add(lblDependanciesList, "2, 14");

		dependencesListEditor = new DependencesListEditor();
		lblDependanciesList.setLabelFor(dependencesListEditor);
		panel.add(dependencesListEditor, "4, 14, fill, fill");

		JLabel lblNote = DefaultComponentFactory.getInstance().createLabel(
				"Note");
		panel.add(lblNote, "2, 16");

		editorPaneNote = new JEditorPane();
		editorPaneNote.setToolTipText("Note for course");
		lblNote.setLabelFor(editorPaneNote);
		editorPaneNote.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(editorPaneNote, "4, 16, fill, fill");

		JButton btnSave = new JButton("Save");
		add(btnSave, "2, 4, center, default");
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
