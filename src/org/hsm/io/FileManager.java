package org.hsm.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

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
		return extensionVerify(getOutputFile(new FileNameExtensionFilter(
				"Portable network graphics image", "png")), "png");
	}

	private File extensionVerify(File file, String ext) {
		if (file == null || file.getName().contains("."))
			return file;
		return new File(file.getPath() + "." + ext);
	}

	public File getOutputFileHtml() {
		return extensionVerify(getOutputFile(new FileNameExtensionFilter(
				"HyperText Markup Language files", "html", "htm")), "html");
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

	public FileInputStream getInputStreamWithInfoDialog(String fileName) {
		String message;
		if (!createFileIfNotExist(fileName))
			return null;

		// open file to load
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			message = "Cannot open file " + fileName + " to read\nMessage: "
					+ e1.getMessage();
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(), message,
					"Cannot open file to read", JOptionPane.WARNING_MESSAGE);
			Control.getInstance().getLogger().log(Level.WARNING, message);
		}
		return in;
	}

	private boolean createFileIfNotExist(String fileName) {
		File file = new File(fileName);
		String message;

		// check file exists
		if (!(file).exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				message = "Cannot create empty file {" + file.getAbsolutePath()
						+ "}\nMessage " + e.getMessage();
				JOptionPane.showMessageDialog(Control.getInstance()
						.getMainWindow(), message, "Cannot create file",
						JOptionPane.WARNING_MESSAGE);
				Control.getInstance().getLogger().log(Level.WARNING, message);
				return false;
			}
		}
		return true;
	}

	public void close(FileInputStream in) {
		if (in != null)
			try {
				in.close();
			} catch (IOException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.SEVERE,
								"Cannot close file input stream\nMessage: "
										+ e.getMessage());
			}
	}

	public FileOutputStream getFileOutputStreamWithDialog(String fileName) {
		// open file to load
		if (!createFileIfNotExist(fileName))
			return null;
		FileOutputStream ou = null;
		try {
			ou = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			Control.getInstance()
					.getLogger()
					.log(Level.SEVERE,
							"Failed to get file output stream{" + fileName
									+ "}\nMessage: " + e.getMessage());
		}
		return ou;
	}

	public void close(FileOutputStream ou) {
		if (ou != null)
			try {
				ou.close();
			} catch (IOException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.SEVERE,
								"Cannot close file output stream\nMessage: "
										+ e.getMessage());
			}
	}
}
