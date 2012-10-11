package org.hsm.view.login;

import javax.swing.JButton;

public class LoginButton extends JButton {

	private LoginWindow loginWindow;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginButton(LoginWindow loginWindow) {
		super("Login");
		this.loginWindow = loginWindow;
		super.setMnemonic('l');
	}

	public LoginWindow getLoginWindow() {
		return loginWindow;
	}

}
