package org.hsm.main;

import javax.swing.JOptionPane;

import org.hsm.control.Control;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Control.getInstance().fire("start");
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null,
					"An error has occured while running application.\nMessage: "
							+ e.getMessage());
		}
	}

}
