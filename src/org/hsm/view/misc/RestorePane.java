package org.hsm.view.misc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

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

import org.hsm.control.Control;
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
	private DefaultTableModel infomodel;
	protected Object[][] data = { { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " },
			{ " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };

	protected String[] columnname = { "File", "Size", "Last Modified" };

	public RestorePane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		JLabel selectlb = DefaultComponentFactory.getInstance().createLabel(
				"Choose backup file to restore");
		add(selectlb, "2, 2, 3, 1");
		
				JButton btnclient = new JButton("From client");
				btnclient.setMnemonic('c');
				add(btnclient, "2, 4, left, default");
				btnclient.setToolTipText("Using backup file on client");
				
						btnclient.addActionListener(new ActionListener() {
				
							@Override
							public void actionPerformed(ActionEvent e) {
								File[] list_file = get_list_file();
				
								if (list_file == null) {
									output.append("Restore cancel by user.\n");
								} else {
									viewFileInfo(list_file);
									output.append("Running....");
									try {
										total_rows = RestoreService.get_instance()
												.client_copyin(list_file);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									if (total_rows == -1) {
										output.append("Failed.\nDatabase restore did not complete successfully.See log for details.\n");
									} else {
										output.append("Done.\nTotal rows:" + total_rows + "\n");
									}
								}
							}
						});
		JButton btnserver = new JButton("From server");
		btnserver.setMnemonic('r');
		add(btnserver, "4, 4, left, default");
		btnserver.setToolTipText("Using backup file on server");
		btnserver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Control.getInstance().setQueryMessage("Under contruction.");
			}
		});
		JLabel backuplb = DefaultComponentFactory.getInstance().createLabel(
				"Backup file");
		add(backuplb, "2, 6, 3, 1");
		JLabel resultlb = DefaultComponentFactory.getInstance().createLabel(
				"Console");
		add(resultlb, "2, 10, 3, 1");

		infomodel = new DefaultTableModel(data, columnname);
		viewFileinfo = new JTable(infomodel);
		viewFileinfo
				.setPreferredScrollableViewportSize(new Dimension(450, 252));
		viewFileinfo.setMinimumSize(new Dimension(400, 30));
		viewFileinfo.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("400px:grow"), }, new RowSpec[] { RowSpec
				.decode("default:grow"), }));
		JScrollPane viewfilescr = new JScrollPane();
		viewfilescr.setMinimumSize(new Dimension(400, 23));
		viewfilescr.setViewportView(viewFileinfo);
		add(viewfilescr, "2, 8, 3, 1, fill, fill");
		output = new JTextArea();
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setToolTipText("Display result.");
		output.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		JScrollPane outputscl = new JScrollPane();
		outputscl.setViewportView(output);
		add(outputscl, "2, 12, 3, 1, fill, fill");
	}

	public File[] get_list_file() {
		File[] list_file;
		list_file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose backup file");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
			return list_file;
		list_file = fileChooser.getSelectedFiles();
		return list_file;
	}

	public void viewFileInfo(File[] list_file) {
		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY hh:mm a");
		for (int i = 0; i < list_file.length; i++) {
			infomodel.setValueAt(list_file[i].getName(), i, 0);
			infomodel.setValueAt(list_file[i].length() + " bytes", i, 1);
			infomodel.setValueAt(sdf.format(list_file[i].lastModified()), i, 2);
		}
		for (int i = list_file.length; i < infomodel.getRowCount(); i++) {
			infomodel.setValueAt("", i, 0);
			infomodel.setValueAt("", i, 1);
			infomodel.setValueAt("", i, 2);
		}
	}
}
