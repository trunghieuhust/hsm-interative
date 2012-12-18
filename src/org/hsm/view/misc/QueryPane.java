package org.hsm.view.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.control.Control;
import org.hsm.model.hedspiObject.HedspiTable;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class QueryPane extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JEditorPane queryInputEntry;
	private JEditorPane messagePane;
	private String lastQuery = "";
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public QueryPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(75dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JLabel lblQuery = DefaultComponentFactory.getInstance().createLabel(
				"Query");
		add(lblQuery, "2, 2, 3, 1");

		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "2, 4, 3, 1, fill, fill");

		queryInputEntry = new JEditorPane();
		scrollPane_1.setViewportView(queryInputEntry);
		queryInputEntry.setToolTipText("Input query to execute");
		lblQuery.setLabelFor(queryInputEntry);
		queryInputEntry.setText("SELECT * FROM room;");

		JButton btnSubmit = new JButton("Submit");
		add(btnSubmit, "2, 6, left, default");
		btnSubmit.setMnemonic('s');
		btnSubmit.setToolTipText("Sumit query");
		btnSubmit.addActionListener(this);

		JButton btnExport = new JButton("Export");
		add(btnExport, "4, 6, left, default");
		btnExport.setMnemonic('x');
		btnExport
				.setToolTipText("Export only values on below table to html file");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model == null)
					return;
				HedspiTable hedspiTable = new HedspiTable("Result of query {"
						+ lastQuery + "}", model);
				hedspiTable.writeToHtmlWithMessageDialog();
			}
		});

		JLabel lblResult = DefaultComponentFactory.getInstance().createLabel(
				"Result");
		add(lblResult, "2, 8, 3, 1");

		JSplitPane splitPane = new JSplitPane();
		add(splitPane, "2, 10, 3, 1, fill, fill");

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JLabel lblMessage = new JLabel("Message");
		panel.add(lblMessage, "2, 2");

		messagePane = new JEditorPane();
		messagePane.setToolTipText("Message received from server");
		lblMessage.setLabelFor(messagePane);
		messagePane.setEditable(false);
		panel.add(messagePane, "2, 4, fill, fill");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Returning data");
		splitPane.setRightComponent(scrollPane);

		table = new JTable();
		table.setToolTipText("Returning data");
		scrollPane.setViewportView(table);
		splitPane.setDividerLocation(100);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String queryStr = queryInputEntry.getText();
		Object[] rs = (Object[]) Control.getInstance().getData("query",
				queryStr);
		messagePane.setText((String) rs[0]);
		int nCol;
		try {
			nCol = (int) rs[1];
		} catch (Exception e1) {
			nCol = 0;
		}
		if (nCol <= 0)
			return;

		lastQuery = queryStr;
		model = new DefaultTableModel();
		table.setModel(model);

		// add data
		boolean first = true;
		for (int i = 2; i < rs.length;) {
			Object[] objs = new Object[nCol];
			for (int j = 0; j < nCol && i < rs.length; j++, i++)
				objs[j] = rs[i];
			if (first)
				for (int k = 0; k < nCol; k++)
					model.addColumn(objs[k]);
			else
				model.addRow(objs);
			first = false;
		}
	}

	public void setQuery(String query) {
		queryInputEntry.setText(query);
	}
}
