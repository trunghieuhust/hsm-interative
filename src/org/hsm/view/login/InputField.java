package org.hsm.view.login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InputField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int LABEL_SIZE = 150;
	private final int TEXT_LENGTH = 10;

	JLabel label;
	JTextField text;

	public InputField(String say) {
		this(say, "", false);
	}

	public InputField(String say, boolean isPasswordField) {
		this(say, "", isPasswordField);
	}

	public InputField(String say, String defaultValue) {
		this(say, defaultValue, false);
	}

	public InputField(String say, String defaultValue, boolean isPasswordField) {
		super();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl.setAlignOnBaseline(true);
		super.setLayout(fl);

		// value
		if (isPasswordField)
			text = new JPasswordField(TEXT_LENGTH);
		else
			text = new JTextField(TEXT_LENGTH);
		text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				((JTextField) e.getComponent()).selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				((JTextField) e.getComponent()).select(0, 0);

			}
		});
		setValue(defaultValue);

		// label
		label = new JLabel();
		label.setLabelFor(text);
		setLabel(say);
		label.setPreferredSize(new Dimension(LABEL_SIZE, label
				.getPreferredSize().height));

		// add
		super.add(label);
		super.add(text);
	}

	public String getValue() {
		return text.getText();
	}

	private void setLabel(String say) {
		label.setText(say);
	}

	public void setMnemonic(char c) {
		label.setDisplayedMnemonic(c);
	}

	private void setValue(String defaultValue) {
		text.setText(defaultValue);
	}
}
