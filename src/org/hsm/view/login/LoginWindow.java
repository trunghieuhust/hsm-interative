package org.hsm.view.login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.hsm.control.Control;
import org.hsm.view.IView;

public class LoginWindow extends IFrameAskToClose implements IView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_LOGIN_SIZE = new Dimension(300, 250);
	private InputField username;
	private InputField password;
	private InputField host;
	private InputField dbname;
	private FlowLayout fl;
	private InputField port;
	private static final String DEFAULT_HOST = "localhost";
	private static final String DEFAULT_PORT = "5432";
	public static final String DEFAULT_DBNAME = "hedspi";
	public static final String DEFAULT_USERNAME = "Admin";
	public static final String DEFAULT_PASSWORD = "hedspi";

	public LoginWindow() {
		setUIBase();
	}

	private void addInputField() {
		// host
		host = new InputField("Host address: ", DEFAULT_HOST);
		host.setMnemonic('h');
		super.getContentPane().add(host);

		// port
		port = new InputField("Port", DEFAULT_PORT);
		port.setMnemonic('t');
		super.getContentPane().add(port);

		// dbname
		dbname = new InputField("Database name", DEFAULT_DBNAME);
		dbname.setMnemonic('d');
		super.getContentPane().add(dbname);

		// username
		username = new InputField("User name", DEFAULT_USERNAME);
		username.setMnemonic('u');
		super.getContentPane().add(username);

		// password
		password = new InputField("Password", DEFAULT_PASSWORD, true);
		password.setMnemonic('p');
		super.getContentPane().add(password);

	}

	private void addOkCancel() {
		JButton loginButton = new LoginButton(this);
		super.getContentPane().add(loginButton);
		super.getRootPane().setDefaultButton(loginButton);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				prop.setProperty("user", username.getValue());
				prop.setProperty("password", password.getValue());
				prop.setProperty("dbname", dbname.getValue());
				prop.setProperty("host", host.getValue());
				prop.setProperty("port", port.getValue());
				Control.getInstance().fireByView(
						((LoginButton) e.getSource()).getLoginWindow(),
						"try-login", prop);
			}
		});

		super.getContentPane().add(new CancelButton(this));
	}

	@Override
	public void close() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure want to quit?",
				"Quit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			super.setVisible(false);
			super.dispose();
		}
		;
	}

	@Override
	public void fire(String command, Object... data) {
		switch (command) {
		case "set-visible":
			super.setVisible((boolean) data[0]);
			break;
		case "login-fail":
			JOptionPane.showMessageDialog(this, "Login failed", "Login failed",
					JOptionPane.WARNING_MESSAGE);
			break;

		default:
			Control.getInstance()
					.getLogger()
					.log(Level.WARNING,
							"You have called LoginWindow an operation that is not supported.\nCommand: {0}",
							command);
		}
	}

	private void setUIBase() {
		// base info
		super.setTitle("Hedspi student manager - login");
		super.setSize(DEFAULT_LOGIN_SIZE);
		super.setResizable(false);

		// add components
		fl = new FlowLayout(FlowLayout.CENTER);
		super.getContentPane().setLayout(fl);

		// input field
		addInputField();
		addOkCancel();
	}

}
