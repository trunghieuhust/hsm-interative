package org.hsm.view.misc;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class MiscPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MiscPane() {
		super(JTabbedPane.TOP);

		JTabbedPane imexPane = new JTabbedPane(JTabbedPane.TOP);
		addTab("Im/Ex(port)", null, imexPane, null);

		JPanel importPane = new JPanel();
		imexPane.addTab("Import", null, importPane, null);

		JPanel exportPane = new JPanel();
		imexPane.addTab("Export", null, exportPane, null);

		JTabbedPane maintainPane = new JTabbedPane(JTabbedPane.TOP);
		addTab("Maintain", null, maintainPane, null);

		JPanel backupPane = new JPanel();
		maintainPane.addTab("Backup", null, backupPane, null);

		JPanel restorePane = new JPanel();
		maintainPane.addTab("Restore", null, restorePane, null);

		JScrollPane queryScrollPane = new JScrollPane();
		addTab("Query", null, queryScrollPane, null);

		QueryPane queryPane = new QueryPane();
		queryScrollPane.setViewportView(queryPane);

		JScrollPane batchDelScrollPane = new JScrollPane();
		addTab("Batch delete", null, batchDelScrollPane, null);

		JPanel batchDelPane = new JPanel();
		batchDelScrollPane.setViewportView(batchDelPane);

		JScrollPane searchScrollPane = new JScrollPane();
		addTab("Search", null, searchScrollPane, null);

		JPanel searchPane = new JPanel();
		searchScrollPane.setViewportView(searchPane);

		JScrollPane statisticScrollPane = new JScrollPane();
		addTab("Statistic", null, statisticScrollPane, null);

		StatisticPane statisticPane = new StatisticPane();
		statisticScrollPane.setViewportView(statisticPane);
	}

}
