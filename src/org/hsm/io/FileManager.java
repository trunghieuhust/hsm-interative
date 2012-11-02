package org.hsm.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hsm.control.Control;

public class FileManager {
	private static FileManager instance;

	public static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	public File getOutputFile(FileFilter filter) {
		boolean exit = false;
		File file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose file to export to");
		fileChooser.setFileFilter(filter);

		do {
			fileChooser.showSaveDialog(Control.getInstance().getMainWindow());
			file = fileChooser.getSelectedFile();
			if (file == null)
				return null;
			exit = true;
			if (file.exists()) {
				int ret = JOptionPane.showConfirmDialog(fileChooser,
						"File exists. Overwrite?", "Overwrite?",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (ret == JOptionPane.CANCEL_OPTION)
					return null;
				exit = ret == JOptionPane.YES_OPTION;
			}
		} while (!exit);
		return file;
	}

	public File getOutputFilePng() {
		return getOutputFile(new FileNameExtensionFilter(
				"Portable network graphics image", "png"));
	}

	public File getOutputFileHtml() {
		return getOutputFile(new FileNameExtensionFilter(
				"HyperText Markup Language files", "html", "htm"));
	}

	public String writeToHtml(String text) {
		File outputFile = getOutputFileHtml();
		if (outputFile == null)
			return "Action might be canceled by user";

		String message = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputFile, "Unicode");
			pw.write(text);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			message = e.getMessage();
		} finally {
			if (pw != null)
				try {
					pw.close();
				} catch (Exception e1) {
					if (message != null)
						message += "\nMessage2: " + e1.getMessage();
					else
						message = e1.getMessage();
				}
		}

		return message;
	}
}
