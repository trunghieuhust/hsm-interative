package org.hsm.view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CancelButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IClosable frame;

	public CancelButton(IClosable frame) {
		super("Cancel");
		super.setMnemonic('c');
		this.frame = frame;
		super.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CancelButton) e.getSource()).getFrameWithClose().close();
			}
		});
	}

	public IClosable getFrameWithClose() {
		return frame;
	}

}
