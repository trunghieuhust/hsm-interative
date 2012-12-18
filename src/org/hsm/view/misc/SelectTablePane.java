package org.hsm.view.misc;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.service.CoreService;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SelectTablePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String[] table_name;
	public static String[] table_size;
	public static int[] isbackup;

	protected HedspiObject[] table_info;
	protected Object[][] data;
	protected String[] columnname = { "Table", "Size", "Select" };
	private String[] tmp = { "", "" };
	protected JTable jtable;
	protected DefaultTableModel model;

	public SelectTablePane() {
		table_info = CoreService.getInstance().rsToSimpleArray(
				CoreService.getInstance().doQueryFunction("get_table_info"));

		isbackup = new int[table_info.length];
		table_name = new String[table_info.length];
		table_size = new String[table_info.length];
		for (int i = 0; i < isbackup.length; i++) {
			isbackup[i] = 1; // default value - backup all
			tmp = table_info[i].getName().split(":");
			table_name[i] = tmp[0];
			table_size[i] = tmp[1];
		}

		model = new DefaultTableModel(data, columnname) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 2)
					return Boolean.class;
				return Object.class;
			}

		};

		jtable = new JTable(model);
		jtable.setPreferredScrollableViewportSize(new Dimension(450, 250));
		jtable.setMinimumSize(new Dimension(400, 0));
		jtable.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		jtable.setToolTipText("Select table");
		jtable.setFillsViewportHeight(false);
		jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtable.setAutoscrolls(true);
		jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtable.setRowSelectionAllowed(true);
		jtable.setColumnSelectionAllowed(false);

		for (int i = 0; i < table_name.length; i++) {
			model.addRow(new Object[] { table_name[i], table_size[i],
					new Boolean(true) });
		}
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("400px:grow"), },
				new RowSpec[] { RowSpec.decode("default:grow"), }));

		jtable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						for (int i = 0; i < isbackup.length; i++) {
							if (jtable.getValueAt(i, 2).equals(true)) {
								isbackup[i] = 1;
							} else
								isbackup[i] = 0;
						}
					}
				});
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "1, 1, fill, fill");
		scrollPane.setViewportView(jtable);

	}

	protected String[] get_table_name() {
		return table_name;
	}

	protected int[] is_backup() {
		return isbackup;
	}

}
