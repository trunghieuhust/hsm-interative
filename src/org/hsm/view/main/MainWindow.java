package org.hsm.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.hsm.control.Control;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainPane contentPane;
	private JCheckBox chckbxLogView;

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

		JMenu mnMain = new JMenu("Main");
		mnMain.setMnemonic('m');
		menuBar.add(mnMain);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(MainWindow.this,
						"Are you sure want to quit?", "Quit?",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					MainWindow.this.setVisible(false);
					MainWindow.this.dispose();
				}
			}
		});
		mnMain.add(mntmQuit);

		JMenu mnView = new JMenu("View");
		mnView.setMnemonic('v');
		menuBar.add(mnView);

		chckbxLogView = new JCheckBox("Log view");
		chckbxLogView.setToolTipText("Open interactive log window");
		chckbxLogView.setMnemonic('l');
		chckbxLogView.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Control.getInstance().fire("setLogViewVisible",
						chckbxLogView.isSelected());
			}
		});
		mnView.add(chckbxLogView);

		JMenu mnTools = new JMenu("Tools");
		mnTools.setMnemonic('t');
		menuBar.add(mnTools);

		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getInstance().fire("setOptionWindowVisble", true);
			}
		});
		mnTools.add(mntmOptions);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('h');
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new org.hsm.view.about.AboutBox()).setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);

		contentPane = new MainPane();
		contentPane.setToolTipText("Status area with time counter");
		setContentPane(contentPane);
	}

	public void setStatus(String status) {
		contentPane.setStatus(status);
		contentPane.repaint();
	}

	public void setLogViewSelected(boolean bool) {
		if (chckbxLogView.isSelected() != bool)
			chckbxLogView.setSelected(bool);

	}
}
