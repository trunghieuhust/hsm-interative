package org.hsm.view.misc;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class StatisticPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldHostaddress;
	private JTextField textFieldPort;
	private JTextField textFieldDatabase;
	private JTextField textFieldUsername;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public StatisticPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnRefresh = new JButton("Refresh all");
		btnRefresh.setToolTipText("Refresh all statistics in list");
		panel.add(btnRefresh, "1, 1");

		JLabel lblHostAddress = DefaultComponentFactory.getInstance()
				.createLabel("Host address");
		panel.add(lblHostAddress, "3, 1, right, default");

		textFieldHostaddress = new JTextField();
		textFieldHostaddress.setToolTipText("Host address");
		lblHostAddress.setLabelFor(textFieldHostaddress);
		panel.add(textFieldHostaddress, "5, 1, fill, default");
		textFieldHostaddress.setEditable(false);
		textFieldHostaddress.setColumns(10);

		JButton btnExport = new JButton("Export");
		btnExport
				.setToolTipText("Export list to html files excluding query string field");
		panel.add(btnExport, "1, 3");

		JLabel lblPort = DefaultComponentFactory.getInstance().createLabel(
				"Port");
		panel.add(lblPort, "3, 3, right, default");

		textFieldPort = new JTextField();
		textFieldPort.setToolTipText("Connection's port");
		lblPort.setLabelFor(textFieldPort);
		panel.add(textFieldPort, "5, 3, fill, default");
		textFieldPort.setEditable(false);
		textFieldPort.setColumns(10);

		JLabel lblDatabase = DefaultComponentFactory.getInstance().createLabel(
				"Database");
		panel.add(lblDatabase, "3, 5, right, default");

		textFieldDatabase = new JTextField();
		textFieldDatabase.setToolTipText("Current database name");
		lblDatabase.setLabelFor(textFieldDatabase);
		panel.add(textFieldDatabase, "5, 5, fill, default");
		textFieldDatabase.setEditable(false);
		textFieldDatabase.setColumns(10);

		JLabel lblUsername = DefaultComponentFactory.getInstance().createLabel(
				"Username");
		panel.add(lblUsername, "3, 7, right, default");

		textFieldUsername = new JTextField();
		textFieldUsername.setToolTipText("Current session's username");
		lblUsername.setLabelFor(textFieldUsername);
		panel.add(textFieldUsername, "5, 7, fill, default");
		textFieldUsername.setEditable(false);
		textFieldUsername.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("List of statistics");
		add(scrollPane, "2, 4, fill, fill");

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmRefreshSelected = new JMenuItem("Refresh selected");
		popupMenu.add(mntmRefreshSelected);

		JMenuItem mntmRemoveSelected = new JMenuItem("Remove selected");
		popupMenu.add(mntmRemoveSelected);

		table = new JTable();
		table.setToolTipText("Right click for more options");
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Description", "Query", "Value" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblRightClickTable = DefaultComponentFactory.getInstance()
				.createLabel("Right click table for options");
		lblRightClickTable.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC,
				12));
		add(lblRightClickTable, "2, 6");
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
