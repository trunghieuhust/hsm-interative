package org.hsm.view.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import org.hsm.control.Control;
import org.hsm.view.IView;


public class MainWindow extends JFrame implements IView{

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
		setBounds(100, 100, 864, 570);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		setContentPane(new MainPane());
	}

	@Override
	public void fire(String command, Object... data) {
		switch (command) {
		case "set-visible":
			super.setVisible((boolean) data[0]);
			break;
		default:
			Control.getInstance()
			.getLogger()
			.log(Level.WARNING,
					"You have called FunctionWindow an operation that is not supported.\nCommand: {0}",
					command);
			break;
		}
	}
}
