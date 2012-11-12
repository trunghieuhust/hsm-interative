package org.hsm.view.misc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import org.hsm.service.RestoreService;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class RestorePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea output;
	private long total_rows;
	private JTable viewFileinfo;
	protected Object[][] data = { { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };
	protected String[] columnname = { "File", "Size", "Last Modified" };

	public RestorePane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));
		JLabel selectlb = DefaultComponentFactory.getInstance().createLabel(
				"Choose backup file to restore");
		add(selectlb, "2,2");
		JLabel backuplb = DefaultComponentFactory.getInstance().createLabel(
				"Backup file");
		add(backuplb, "2, 6");
		JLabel resultlb = DefaultComponentFactory.getInstance().createLabel(
				"Console");
		add(resultlb, "2, 10");
		viewFileInfo(null);
		viewFileinfo
				.setPreferredScrollableViewportSize(new Dimension(450, 250));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 8, fill, fill");
		scrollPane.setViewportView(viewFileinfo);
		scrollPane.setAlignmentY(TOP_ALIGNMENT);
		output = new JTextArea();
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setToolTipText("Display result.");
		output.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		JScrollPane outputscl = new JScrollPane();
		outputscl.setViewportView(output);
		add(outputscl, "2, 12, fill, fill");
		JPanel btnpanel = new JPanel();
		btnpanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnclient = new JButton("From client");
		btnclient.setToolTipText("Using backup file on client");
		JButton btnserver = new JButton("From server");
		btnserver.setToolTipText("Using backup file on server");

		btnclient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File[] list_file = get_list_file();
				viewFileInfo(list_file);
				output.append("Running....");
				try {
					total_rows = RestoreService.get_instance().client_copyin(
							list_file);
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
		add(btnpanel, "2, 4, fill, fill");
	}

	public File[] get_list_file() {
		File[] list_file;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose backup file");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
			return null;
		list_file = fileChooser.getSelectedFiles();
		return list_file;
	}

	public void viewFileInfo(File[] list_file) {
		DefaultTableModel model;
		model = new DefaultTableModel(data, columnname);
		viewFileinfo = new JTable(model);
		if (list_file == null)
			return;
		for (int i = 0; i < list_file.length; i++) {
			model.addRow(new Object[] { list_file[i].getName(),
					list_file[i].length() + "bytes",
					list_file[i].lastModified() });
		}
	}
}
