package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.hsm.service.BackupService;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class BackupPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea output;
	private long total_rows;

	public BackupPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		JLabel selectlb = DefaultComponentFactory.getInstance().createLabel(
				"Select table");
		add(selectlb, "2, 2, 3, 1");

		JRadioButton ckbcsv = new JRadioButton("CSV");
		ckbcsv.setToolTipText("Back up to CSV file");
		ckbcsv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		JRadioButton ckbtxt = new JRadioButton("Plain text");
		ckbtxt.setToolTipText("Back up to plain text file.");
		ckbtxt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		ButtonGroup ckb_file_type = new ButtonGroup();
		ckb_file_type.add(ckbtxt);
		ckb_file_type.add(ckbcsv);

		// add(lblFileType, "2, 8");
		JPanel ckbpanel = new JPanel();
		ckbpanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));
		ckbpanel.add(ckbtxt, "2,1");
		ckbpanel.add(ckbcsv, "4,1");
		// add(ckbpanel, "2,10");
		SelectTablePane selecttable = new SelectTablePane();
		selecttable.jtable.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		add(selecttable, "2, 4, 3, 1, fill, fill");

		JLabel backuplb = DefaultComponentFactory.getInstance().createLabel(
				"Backup to");
		add(backuplb, "2, 6");
		
				JButton btnclient = new JButton("Client backup");
				add(btnclient, "2, 8, left, default");
				btnclient.setToolTipText("Backup to client");
				btnclient.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String url = getOutputFolder();
						output.append("Running....");
						try {
							total_rows = BackupService.get_instance().client_copyout(
									url);
						} catch (IOException e1) {
							output.append("Backup failed.See log for details\n");
							e1.printStackTrace();
						}
						output.append("Done.\nTotal rows:" + total_rows + "\n");
					}
				});
		JButton btnserver = new JButton("Server backup");
		add(btnserver, "4, 8, left, default");
		btnserver.setToolTipText("Backup to server");
		
				btnserver.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
		
					}
				});
		JLabel resultlb = DefaultComponentFactory.getInstance().createLabel(
				"Console");
		add(resultlb, "2, 10, 3, 1");
		output = new JTextArea();
		add(output, "2, 12, 3, 1, fill, fill");
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setToolTipText("Display result.");
		output.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
	}

	public String getOutputFolder() {
		String url;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose folder of backup file");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
			return null;
		url = fileChooser.getSelectedFile().toString();
		return url;
	}
}
