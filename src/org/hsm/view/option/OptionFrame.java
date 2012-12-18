package org.hsm.view.option;

import javax.swing.JFrame;

public class OptionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public OptionFrame() {
		setVisible(false);
		setBounds(100, 100, 450, 300);
		setContentPane(new OptionPane());
	}

	public static void setUI() {
		OptionPane.setUI();
	}
}
