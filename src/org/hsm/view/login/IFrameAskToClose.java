package org.hsm.view.login;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.hsm.control.Control;

public abstract class IFrameAskToClose extends JFrame implements IClosable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IFrameAskToClose() {
		super();
		setWindowOperation();
		setEscapeToClose();
	}

	private void setEscapeToClose() {
		super.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					((IClosable) e.getComponent()).close();

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
		});

	}

	private void setWindowOperation() {
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		super.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				Control.getInstance().fire("exit");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				((IClosable) e.getComponent()).close();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
	}

}
