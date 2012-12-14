package org.hsm.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hsm.control.Control;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class LogViewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JCheckBox chckbxAlwayOnTop;
	private JEditorPane editorPane;
	private JButton btnClose;

	/**
	 * Create the frame.
	 */
	public LogViewFrame() {
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Control.getInstance().fire("setLogViewVisible", false);
			}
		});
		setTitle("Log view");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		chckbxAlwayOnTop = new JCheckBox("Alway on top");
		chckbxAlwayOnTop.setMnemonic('a');
		chckbxAlwayOnTop.setSelected(true);
		chckbxAlwayOnTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				LogViewFrame.this.setAlwaysOnTop(chckbxAlwayOnTop.isSelected());
			}
		});
		contentPane.add(chckbxAlwayOnTop, "2, 2");

		JButton btnClear = new JButton("Clear");
		btnClear.setMnemonic('c');
		btnClear.setToolTipText("Clear log view not affecting log file");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.setText("");
			}
		});
		contentPane.add(btnClear, "2, 4");

		btnClose = new JButton("Close");
		btnClose.setMnemonic('s');
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Control.getInstance().fire("setLogViewVisible", false);
			}
		});
		btnClose.setToolTipText("Hide log view pane");
		contentPane.add(btnClose, "2, 6");

		JLabel lblLogViewArea = DefaultComponentFactory.getInstance()
				.createTitle("Log view area");
		contentPane.add(lblLogViewArea, "2, 8");

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "2, 10, fill, fill");

		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		lblLogViewArea.setLabelFor(editorPane);
		editorPane.setToolTipText("Log fetcher");
		scrollPane.setViewportView(editorPane);
	}

	public void appendLog(String all) {
		editorPane.setText(editorPane.getText() + all + "\n");
	}
}
