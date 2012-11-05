package org.hsm.view.misc;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.io.FileManager;
import org.hsm.model.hedspiObject.HedspiTable;

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
	private DefaultTableModel model;
	private static final String STATISTIC_FILE = "hedspi-statistic.properties";
	private static final String DESCRIPTION_NAME = "description";
	private static final String QUERYSTR_NAME = "querystr";
	private static final String RESULT_NAME = "result";
	private static final String COUNT_NAME = "count";
	private static final String[][] DEFAULT_VALUES = new String[][] {
			new String[] { "Number of rooms", "SELECT count(rm) FROM room", "" },
			new String[] { "Number of students",
					"SELECT count(ct) FROM student", "" },
			new String[] { "Number of lecturers",
					"SELECT count(ct) FROM lecturer", "" },
			new String[] { "Number of student classes",
					"SELECT count(cl) FROM class", "" },
			new String[] { "Number of districts",
					"SELECT count(dt) FROM district", "" },
			new String[] { "Number of cities", "SELECT count(cy) FROM city", "" },
			new String[] { "Number of courses", "SELECT count(ce) FROM course",
					"" },
			new String[] { "Number of faculties",
					"SELECT count(fc) FROM faculty", "" },
			new String[] { "Number of teaching classes",
					"SELECT count(ad) FROM attend", "" },
			new String[] {
					"Maximal point that student achieved",
					"SELECT result || ' (' || concat(first, ' ', last) || ')'\n"
							+ "FROM student JOIN (SELECT result, student_ct AS ct\n"
							+ "FROM attend\n"
							+ "WHERE result = (SELECT max(result) FROM attend)) AS tmp USING (ct)",
					"" },
			new String[] {
					"Minimal point that student get",
					"SELECT result || ' (' || concat(first, ' ', last) || ')'\n"
							+ "FROM student JOIN (SELECT result, student_ct AS ct\n"
							+ "FROM attend\n"
							+ "WHERE result = (SELECT min(result) FROM attend)) AS tmp USING (ct)",
					"" },
			new String[] { "Average point in all teaching classes",
					"SELECT avg(result) FROM attend", "" },
			new String[] {
					"Maximal number of students in all classes",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM class JOIN (WITH class_with_counter AS (SELECT count(ct) AS cnt, cl\n"
							+ "FROM student RIGHT OUTER JOIN class USING (cl)\n"
							+ "GROUP BY cl)\n"
							+ "SELECT cnt, cl\n"
							+ "FROM class_with_counter\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM class_with_counter)) AS tmp USING (cl)",
					"" },
			new String[] {
					"Minimal number of students in all classes",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM class JOIN (WITH class_with_counter AS (SELECT count(ct) AS cnt, cl\n"
							+ "FROM student RIGHT OUTER JOIN class USING (cl)\n"
							+ "GROUP BY cl)\n"
							+ "SELECT cnt, cl\n"
							+ "FROM class_with_counter\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM class_with_counter)) AS tmp USING (cl)",
					"" },
			new String[] {
					"Average number of students in all classes",
					"SELECT avg(cnt) FROM (SELECT count(ct) AS cnt, cl FROM student GROUP BY cl) AS class_with_counter",
					"" },
			new String[] {
					"Maximal number of lecturers in all faculties",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM faculty JOIN (\n"
							+ "WITH faculty_with_counter AS (\n"
							+ "SELECT count(ct) AS cnt, fc\n"
							+ "FROM lecturer RIGHT OUTER JOIN faculty USING (fc)\n"
							+ "GROUP BY fc)\n"
							+ "SELECT cnt, fc\n"
							+ "FROM faculty_with_counter\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM faculty_with_counter)) AS tmp USING (fc)",
					"" },
			new String[] {
					"Minimal number of lecturers in all faculties",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM faculty JOIN (\n"
							+ "WITH faculty_with_counter AS (\n"
							+ "SELECT count(ct) AS cnt, fc\n"
							+ "FROM lecturer RIGHT OUTER JOIN faculty USING (fc)\n"
							+ "GROUP BY fc)\n"
							+ "SELECT cnt, fc\n"
							+ "FROM faculty_with_counter\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM faculty_with_counter)) AS tmp USING (fc)",
					"" },
			new String[] {
					"Average number of lecturers in all faculties",
					"SELECT avg(cnt) FROM (SELECT count(ct) AS cnt FROM lecturer GROUP BY fc) AS tmp",
					"" },
			new String[] {
					"Maximal number of districts in all cities",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM city JOIN (\n"
							+ "WITH city_with_counter AS (\n"
							+ "SELECT count(dt) AS cnt, cy\n"
							+ "FROM district RIGHT OUTER JOIN city USING (cy)\n"
							+ "GROUP BY cy)\n"
							+ "SELECT cnt, cy\n"
							+ "FROM city_with_counter\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM city_with_counter)) AS tmp USING (cy) ",
					"" },
			new String[] {
					"Minimal number of districts in all cities",
					"SELECT cnt || ' (' || name || ')'\n"
							+ "FROM city JOIN (\n"
							+ "WITH city_with_counter AS (\n"
							+ "SELECT count(dt) AS cnt, cy\n"
							+ "FROM district RIGHT OUTER JOIN city USING (cy)\n"
							+ "GROUP BY cy)\n"
							+ "SELECT cnt, cy\n"
							+ "FROM city_with_counter\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM city_with_counter)) AS tmp USING (cy) ",
					"" },
			new String[] {
					"Average number of districts in all cities",
					"SELECT avg(cnt) FROM( SELECT count(dt) AS cnt, cy FROM district RIGHT OUTER JOIN city USING (cy) GROUP BY cy) AS city_with_counter",
					"" },
			new String[] {
					"The best average point",
					"WITH student_avg_point AS (SELECT avg(result) AS apoint, student_ct AS ct FROM attend GROUP BY student_ct)\n"
							+ "SELECT apoint || ' (' || concat(first, ' ', last) || ')'\n"
							+ "FROM student JOIN student_avg_point USING (ct)\n"
							+ "WHERE apoint = (SELECT max(apoint) FROM student_avg_point)",
					"" },
			new String[] {
					"The worst average point",
					"WITH student_avg_point AS (SELECT avg(result) AS apoint, student_ct AS ct FROM attend GROUP BY student_ct)\n"
							+ "SELECT apoint || ' (' || concat(first, ' ', last) || ')'\n"
							+ "FROM student JOIN student_avg_point USING (ct)\n"
							+ "WHERE apoint = (SELECT min(apoint) FROM student_avg_point)",
					"" },
			new String[] {
					"Average of passed courses in all students",
					"SELECT avg(cnt) FROM (SELECT count(DISTINCT ce) AS cnt FROM attend WHERE is_passed IS TRUE GROUP BY student_ct) AS tmp",
					"" },
			new String[] {
					"The best student with most passed courses",
					"WITH student_passed_course AS (SELECT count(DISTINCT ce) AS cnt, student_ct AS ct FROM attend WHERE is_passed IS TRUE GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM student LEFT OUTER JOIN student_passed_course USING (ct)\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM student_passed_course)",
					"" },
			new String[] {
					"The worst student with least passed courses",
					"WITH student_passed_course AS (SELECT count(DISTINCT ce) AS cnt, student_ct AS ct FROM attend WHERE is_passed IS TRUE GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM student LEFT OUTER JOIN student_passed_course USING (ct)\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM student_passed_course)",
					"" },
			new String[] {
					"The best student with most passed classes",
					"WITH student_passed_course AS (SELECT count(ad) AS cnt, student_ct AS ct FROM attend WHERE is_passed IS TRUE GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM student LEFT OUTER JOIN student_passed_course USING (ct)\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM student_passed_course)",
					"" },
			new String[] {
					"The worst student with least passed classes",
					"WITH student_passed_course AS (SELECT count(ad) AS cnt, student_ct AS ct FROM attend WHERE is_passed IS TRUE GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM student LEFT OUTER JOIN student_passed_course USING (ct)\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM student_passed_course)",
					"" },
			new String[] {
					"Lecturer with the most teached classes",
					"WITH lecturer_with_counter AS (SELECT count(ad) AS cnt, lecturer_ct AS ct FROM attend GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM lecturer LEFT OUTER JOIN lecturer_with_counter USING (ct)\n"
							+ "WHERE cnt = (SELECT max(cnt) FROM lecturer_with_counter)",
					"" },
			new String[] {
					"Lecturer with the least teached classes",
					"WITH lecturer_with_counter AS (SELECT count(ad) AS cnt, lecturer_ct AS ct FROM attend GROUP BY ct)\n"
							+ "SELECT concat(first, ' ', last) || ' (' || cnt || ')'\n"
							+ "FROM lecturer LEFT OUTER JOIN lecturer_with_counter USING (ct)\n"
							+ "WHERE cnt = (SELECT min(cnt) FROM lecturer_with_counter)",
					"" } };
	private static Properties defaultProps;
	private JPopupMenu popup;

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
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] arr = new int[model.getRowCount()];
				for (int i = 0; i < arr.length; i++)
					arr[i] = i;
				refresh(arr);
			}
		});
		btnRefresh.setToolTipText("Refresh all statistics in list");
		panel.add(btnRefresh, "1, 1");

		JLabel lblHostAddress = DefaultComponentFactory.getInstance()
				.createLabel("Host address");
		panel.add(lblHostAddress, "3, 1, right, default");

		textFieldHostaddress = new JTextField((String) Control.getInstance()
				.getData("getLoginInfo", "host"));
		textFieldHostaddress.setToolTipText("Host address");
		lblHostAddress.setLabelFor(textFieldHostaddress);
		panel.add(textFieldHostaddress, "5, 1, fill, default");
		textFieldHostaddress.setEditable(false);
		textFieldHostaddress.setColumns(10);

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HedspiTable hedspiTable = new HedspiTable(
						"General and user-defined statistic information",
						"Label", "value");
				hedspiTable.setIsTablePrint(false);
				// server information
				hedspiTable.addValue("Host address",
						textFieldHostaddress.getText());
				hedspiTable.addValue("Port", textFieldPort.getText());
				hedspiTable.addValue("Database", textFieldDatabase.getText());
				hedspiTable.addValue("Username", textFieldUsername.getText());
				hedspiTable.addValue("", "");
				// statistic info
				int n = model.getRowCount();
				for (int i = 0; i < n; i++)
					hedspiTable.addValue((String) model.getValueAt(i, 0),
							(String) model.getValueAt(i, 2));
				// write
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});
		btnExport
				.setToolTipText("Export list to html files excluding query string field");
		panel.add(btnExport, "1, 3");

		JLabel lblPort = DefaultComponentFactory.getInstance().createLabel(
				"Port");
		panel.add(lblPort, "3, 3, right, default");

		textFieldPort = new JTextField((String) Control.getInstance().getData(
				"getLoginInfo", "port"));
		textFieldPort.setToolTipText("Connection's port");
		lblPort.setLabelFor(textFieldPort);
		panel.add(textFieldPort, "5, 3, fill, default");
		textFieldPort.setEditable(false);
		textFieldPort.setColumns(10);

		JButton btnSaveList = new JButton("Save list");
		btnSaveList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOutputStream ou = FileManager.getInstance()
						.getFileOutputStreamWithDialog(STATISTIC_FILE);
				if (ou == null) {
					return;
				}
				String comment = "Save list of statistics values with username{"
						+ textFieldUsername.getText()
						+ "}, host{"
						+ textFieldHostaddress.getText()
						+ "}, port{"
						+ textFieldPort.getText()
						+ "}, database{"
						+ textFieldDatabase.getText()
						+ "} at "
						+ (new Date()).toString();
				Control.getInstance().getLogger().log(Level.INFO, comment);
				try {
					Properties props = new Properties();
					int n = model.getRowCount();
					props.setProperty(COUNT_NAME, String.valueOf(n));
					for (int i = 0; i < n; i++) {
						props.setProperty(DESCRIPTION_NAME + i,
								(String) model.getValueAt(i, 0));
						props.setProperty(QUERYSTR_NAME + i,
								(String) model.getValueAt(i, 1));
						props.setProperty(RESULT_NAME + i,
								(String) model.getValueAt(i, 2));
					}
					props.store(ou, comment);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(
							Control.getInstance().getMainWindow(),
							"Save list of statistics failed\nMessage: "
									+ e1.getMessage(), "Saving failed",
							JOptionPane.WARNING_MESSAGE);
				} finally {
					FileManager.getInstance().close(ou);
				}
				JOptionPane.showMessageDialog(Control.getInstance()
						.getMainWindow(), "Save list of statistics ok",
						"Saveing ok", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSaveList.setToolTipText("Save list for next sessions");
		panel.add(btnSaveList, "1, 5");

		JLabel lblDatabase = DefaultComponentFactory.getInstance().createLabel(
				"Database");
		panel.add(lblDatabase, "3, 5, right, default");

		textFieldDatabase = new JTextField((String) Control.getInstance()
				.getData("getLoginInfo", "database"));
		textFieldDatabase.setToolTipText("Current database name");
		lblDatabase.setLabelFor(textFieldDatabase);
		panel.add(textFieldDatabase, "5, 5, fill, default");
		textFieldDatabase.setEditable(false);
		textFieldDatabase.setColumns(10);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setToolTipText("Back to last saved");
		panel.add(btnReset, "1, 7");

		JLabel lblUsername = DefaultComponentFactory.getInstance().createLabel(
				"Username");
		panel.add(lblUsername, "3, 7, right, default");

		textFieldUsername = new JTextField((String) Control.getInstance()
				.getData("getLoginInfo", "username"));
		textFieldUsername.setToolTipText("Current session's username");
		lblUsername.setLabelFor(textFieldUsername);
		panel.add(textFieldUsername, "5, 7, fill, default");
		textFieldUsername.setEditable(false);
		textFieldUsername.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		add(scrollPane, "2, 4, fill, fill");

		popup = new JPopupMenu();
		addPopup(scrollPane, popup);

		JMenuItem mntmRefreshSelected = new JMenuItem("Refresh selected");
		mntmRefreshSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(table.getSelectedRows());
			}
		});
		popup.add(mntmRefreshSelected);

		JMenuItem mntmRemoveSelected = new JMenuItem("Remove selected");
		mntmRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] arr = table.getSelectedRows();
				Arrays.sort(arr);
				for (int i = arr.length - 1; i >= 0; i--)
					model.removeRow(arr[i]);
			}
		});
		popup.add(mntmRemoveSelected);

		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new String[] { "Number of rooms",
						"SELECT count(rm) FROM room", "" });
			}
		});
		popup.add(mntmAdd);

		JMenuItem mntmSelectAll = new JMenuItem("Select all");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.selectAll();
			}
		});
		popup.add(mntmSelectAll);

		JMenuItem mntmSelectNone = new JMenuItem("Select none");
		mntmSelectNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();
			}
		});
		popup.add(mntmSelectNone);

		JMenuItem mntmUseDefault = new JMenuItem("Use default");
		mntmUseDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[][] values = getValuesFromProps(getDefaultProps());
				if (values == null) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Reset failed\nMessage: invalid internal data",
							"Reset failed", JOptionPane.WARNING_MESSAGE);
					return;
				}
				load(values);
				JOptionPane.showMessageDialog(Control.getInstance()
						.getMainWindow(),
						"Reset to default internal values ok", "Reset ok",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		popup.add(mntmUseDefault);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showMenu(e);
			}
		});
		table.setToolTipText("Right click for more options");
		model = new DefaultTableModel(new Object[][] {}, new String[] {
				"Description", "Query", "Value" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		table.setModel(model);
		scrollPane.setViewportView(table);

		JLabel lblRightClickTable = DefaultComponentFactory.getInstance()
				.createLabel("Right click table for options");
		lblRightClickTable.setToolTipText("List of statistics");
		lblRightClickTable.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC,
				12));
		add(lblRightClickTable, "2, 6");
	}

	protected void refresh(int[] selectedRows) {
		int cnt = 0;
		String message = null;
		for (int i = 0; i < selectedRows.length; i++) {
			message = refresh(selectedRows[i]);
			cnt += (message == null) ? 1 : 0;
		}

		JOptionPane.showMessageDialog(Control.getInstance().getMainWindow(),
				"Refresh sumarize: " + cnt + " / " + selectedRows.length
						+ " (success/all)\nSee log file for more information",
				"Refresh sumarize", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Refresh row number 0 in table
	 * 
	 * @param i
	 *            row index (0-based)
	 * @return return <code>null</code> when success, otherwise return error
	 *         message
	 */
	private String refresh(int i) {
		String query = (String) model.getValueAt(i, 1);
		String[] result = (String[]) Control.getInstance().getData(
				"getStatisticQueryResult", query);
		if (result[0] != null)
			model.setValueAt("<<error>>" + result[0], i, 2);
		else
			model.setValueAt(result[1], i, 2);
		return result[0];
	}

	private Properties getProperties() {
		String message;

		FileInputStream in = FileManager.getInstance()
				.getInputStreamWithInfoDialog(STATISTIC_FILE);
		Properties props;
		if (in == null) {
			props = getDefaultProps();
			return props;
		}

		// load
		props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			message = "Cannot load or invalid configuration file {"
					+ STATISTIC_FILE + "}\nMessage: " + e.getMessage();
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(), message,
					"Cannot load configuration file",
					JOptionPane.WARNING_MESSAGE);
			Control.getInstance().getLogger().log(Level.WARNING, message);
			props = getDefaultProps();
		} finally {
			FileManager.getInstance().close(in);
		}

		// check
		if (!isValidProp(props))
			props = getDefaultProps();

		return props;
	}

	private String[][] getValuesFromProps(Properties props) {
		// get row count
		int n;
		try {
			n = Integer.parseInt(props.getProperty(COUNT_NAME));
		} catch (NumberFormatException e) {
			return null;
		}

		// clear
		int tmp = model.getRowCount();
		for (int i = tmp - 1; i >= 0; i--)
			model.removeRow(i);

		// get data
		String[][] ret = new String[n][3];
		for (int i = 0; i < n; i++) {
			String description = props.getProperty(DESCRIPTION_NAME + i, "");
			if (description == null)
				return null;

			String queryStr = props.getProperty(QUERYSTR_NAME + i, "");
			if (queryStr == null)
				return null;

			String result = props.getProperty(RESULT_NAME + i, "");
			if (result == null)
				return null;

			ret[i][0] = description;
			ret[i][1] = queryStr;
			ret[i][2] = result;
		}

		return ret;
	}

	private boolean isValidProp(Properties props) {
		return getValuesFromProps(props) != null;
	}

	private static Properties getDefaultProps() {
		if (defaultProps != null)
			return defaultProps;
		Control.getInstance()
				.getLogger()
				.log(Level.INFO,
						"Use default internal default statistic values");
		// JOptionPane.showMessageDialog(Control.getInstance().getMainWindow(),
		// "Use default internal statistic values",
		// "Use default internal values", JOptionPane.INFORMATION_MESSAGE);
		defaultProps = new Properties();
		for (int i = 0; i < DEFAULT_VALUES.length; i++) {
			defaultProps
					.setProperty(DESCRIPTION_NAME + i, DEFAULT_VALUES[i][0]);
			defaultProps.setProperty(QUERYSTR_NAME + i, DEFAULT_VALUES[i][1]);
			defaultProps.setProperty(RESULT_NAME + i, DEFAULT_VALUES[i][2]);
		}
		defaultProps.setProperty(COUNT_NAME,
				String.valueOf(DEFAULT_VALUES.length));
		return defaultProps;
	}

	protected void reset() {
		String[][] values = getValuesFromProps(getProperties());
		if (values == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Reset failed\nMessage: invalid data", "Reset failed",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		load(values);
		JOptionPane.showMessageDialog(Control.getInstance().getMainWindow(),
				"Reset data from last saved ok", "Reset ok",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void load(String[][] values) {
		for (int i = 0; i < values.length; i++)
			model.addRow(values[i]);
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
