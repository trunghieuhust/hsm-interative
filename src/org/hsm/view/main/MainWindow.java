package org.hsm.view.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import org.hsm.control.Control;


public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(e.getWindow(),
						"Are you sure want to quit?", "Quit?",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					e.getWindow().setVisible(false);
					e.getWindow().dispose();
				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				Control.getInstance().fire("exit");
			}
		});
		setTitle("Hedspi Student Manager");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 896, 587);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		setContentPane(new MainPane());
	}
}
