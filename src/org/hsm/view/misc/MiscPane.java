package org.hsm.view.misc;

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

		JScrollPane queryScrollPane = new JScrollPane();
		addTab("Query", null, queryScrollPane, null);

		QueryPane queryPane = new QueryPane();
		queryScrollPane.setViewportView(queryPane);

		JScrollPane searchScrollPane = new JScrollPane();
		addTab("Search", null, searchScrollPane, null);

		SearchPane searchPane = new SearchPane();
		searchScrollPane.setViewportView(searchPane);
		
				JTabbedPane maintainPane = new JTabbedPane(JTabbedPane.TOP);
				addTab("Maintain", null, maintainPane, null);
				
						BackupPane backupPane = new BackupPane();
						maintainPane.addTab("Backup", null, backupPane, null);
						
								RestorePane restorePane = new RestorePane();
								maintainPane.addTab("Restore", null, restorePane, null);

		JScrollPane statisticScrollPane = new JScrollPane();
		addTab("Statistic", null, statisticScrollPane, null);

		StatisticPane statisticPane = new StatisticPane();
		statisticScrollPane.setViewportView(statisticPane);
		
				JTabbedPane imexPane = new JTabbedPane(JTabbedPane.TOP);
				addTab("Im/Ex(port)", null, imexPane, null);
				
						ImportPane importPane = new ImportPane();
						imexPane.addTab("Import", null, importPane, null);
						
								ExportPane exportPane = new ExportPane();
								imexPane.addTab("Export", null, exportPane, null);
	}

}
