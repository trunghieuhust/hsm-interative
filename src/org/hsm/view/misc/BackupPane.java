package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));
		JLabel selectlb = DefaultComponentFactory.getInstance().createLabel(
				"Select table");
		add(selectlb, "2,2");
		JLabel backuplb = DefaultComponentFactory.getInstance().createLabel(
				"Backup to");
		add(backuplb, "2, 8");
		JLabel resultlb = DefaultComponentFactory.getInstance().createLabel(
				"Console");
		add(resultlb, "2, 12");
		SelectTablePane selecttable = new SelectTablePane();
		selecttable.jtable.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		add(selecttable, "2, 6, fill, fill");
		output = new JTextArea();
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setToolTipText("Display result.");
		output.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		JScrollPane outputscl = new JScrollPane();
		outputscl.setViewportView(output);
		add(outputscl, "2, 14, fill, fill");
		JPanel btnpanel = new JPanel();
		btnpanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnclient = new JButton("Client backup");
		btnclient.setToolTipText("Backup to client");
		JButton btnserver = new JButton("Server backup");
		btnserver.setToolTipText("Backup to server");

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
		btnserver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnpanel.add(btnclient, "1,1");
		btnpanel.add(btnserver, "3,1");
		add(btnpanel, "2, 10, fill, fill");
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
