package org.hsm.io;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.hsm.control.Control;

public class FileManager {
	private static FileManager instance;

	public static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	public File getOutputFile() {
		boolean exit = false;
		File file = null;
		do{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose file to export to");
		fileChooser.showSaveDialog(Control.getInstance().getMainWindow());
		file = fileChooser.getSelectedFile();
		if (file == null)
			return null;
		exit = true;
		if (file.exists()){
			int ret = JOptionPane.showConfirmDialog(fileChooser, "File exists. Overwrite?", "Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return null;
			exit = ret == JOptionPane.YES_OPTION;
		}
		} while (!exit);
		return file;
	}

}
