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

import org.hsm.control.Control;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class QueryPane extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JEditorPane queryInputEntry;
	private JEditorPane messagePane;

	/**
	 * Create the panel.
	 */
	public QueryPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(41dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblQuery = DefaultComponentFactory.getInstance().createLabel("Query");
		add(lblQuery, "2, 2");
		
		queryInputEntry = new JEditorPane();
		queryInputEntry.setText("SELECT * FROM room;");
		add(queryInputEntry, "2, 4, fill, fill");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(this);
		add(btnSubmit, "2, 6, left, default");
		
		JLabel lblResult = DefaultComponentFactory.getInstance().createLabel("Result");
		add(lblResult, "2, 8");
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, "2, 10, fill, fill");
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblMessage = new JLabel("Message");
		panel.add(lblMessage, "2, 2");
		
		messagePane = new JEditorPane();
		messagePane.setEditable(false);
		panel.add(messagePane, "2, 4, fill, fill");
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		splitPane.setDividerLocation(100);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] rs = (String[]) Control.getInstance().getData("query", queryInputEntry.getText());
		messagePane.setText(rs[0]);
		int nRow;
		try{
			nRow = Integer.parseInt(rs[1]);
		} catch (NumberFormatException e1){
			nRow = 0;
		}
		// TODO Auto-generated method stub
		
	}
}
