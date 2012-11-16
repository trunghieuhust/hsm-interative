package org.hsm.model.hedspiObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hsm.control.Control;
import org.hsm.io.FileManager;

public class HedspiTable {
	private String[] headers;
	private ArrayList<String>[] values;
	private String title;
	private boolean isPrintWithOrd;
	private boolean isShowHeader;
	private boolean isShowBorder;

	public HedspiTable(String title, String... headers) {
		setIsTablePrint(true);
		this.title = title;

		setHeaders(headers);
	}

	@SuppressWarnings("unchecked")
	private void setHeaders(String... headers) {
		this.headers = new String[headers.length];
		for (int i = 0; i < headers.length; i++)
			this.headers[i] = headers[i];

		values = new ArrayList[this.headers.length];
		for (int i = 0; i < this.headers.length; i++)
			values[i] = new ArrayList<String>();
	}

	public HedspiTable(String title, DefaultTableModel model) {
		setIsTablePrint(true);
		this.title = title;

		// header
		int n = model.getColumnCount();
		String[] header = new String[n];
		for (int i = 0; i < n; i++)
			header[i] = model.getColumnName(i);
		setHeaders(header);

		// data
		int cnt = model.getRowCount();
		for (int i = 0; i < cnt; i++) {
			String[] data = new String[n];
			for (int j = 0; j < n; j++)
				data[j] = model.getValueAt(i, j).toString();
			addValue(data);
		}
	}

	public void addValue(String... v) {
		int t = Math.min(v.length, values.length);
		for (int i = 0; i < t; i++)
			values[i].add(v[i]);
		for (int i = t + 1; i < values.length; i++)
			values[i].add("");
	}

	public String getHtmlText() {
		String ret = "<body>\n<center><h1>Hedspi student manager&copy;</h1></center><br>\n";

		ret += getTableHtml();

		// finish
		ret += "</body>";
		return ret;
	}

	public String getTableHtml() {
		String ret = "";

		// title
		ret += "<h3>" + title + "</h3><br>\n";
		if (headers.length == 0) {
			ret += "Empty table!\n</body>";
			return ret;
		}

		ret += "<table border=\"" + (isShowBorder ? "1" : "0") + "\">\n";
		if (isShowHeader) {
			// header
			ret += "<tr>\n";
			// order
			if (isPrintWithOrd)
				ret += "<th>Ord</th>\n";
			for (int i = 0; i < headers.length; i++)
				ret += "<th>" + escapeHtmlHighUnicode(headers[i]) + "</th>\n";
			ret += "</tr>\n";
		}

		// data
		for (int i = 0; i < values[0].size(); i++) {
			ret += "<tr>\n";
			// order
			if (isPrintWithOrd)
				ret += "<td>" + (i + 1) + "</td>\n";
			for (int j = 0; j < values.length; j++) {
				ret += "<td>" + escapeHtmlHighUnicode(values[j].get(i))
						+ "</td>\n";
			}
			ret += "</tr>\n";
		}
		ret += "</table>\n";

		return ret;
	}

	private String escapeHtmlHighUnicode(String str) {
		String utf8str = StringEscapeUtils.escapeHtml4(str);
		try {
			utf8str = new String(str.getBytes(Charset.forName("UTF8")), "UTF8");
		} catch (UnsupportedEncodingException e) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, e.getMessage());
		}
		return StringEscapeUtils.escapeHtml4(utf8str);
	}

	private void setShowHeader(boolean isShowHeader) {
		this.isShowHeader = isShowHeader;
	}

	private void setShowBorder(boolean isShowBorder) {
		this.isShowBorder = isShowBorder;
	}

	private void setPrintWithOrd(boolean isPrintWithOrd) {
		this.isPrintWithOrd = isPrintWithOrd;
	}

	public void writeToHtmlWithMessageDialog() {
		String message = writeToHtml();
		if (message == null)
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Export to html success", "Export success",
					JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Export to html failed\nMessage: " + message,
					"Export failed", JOptionPane.ERROR_MESSAGE);
	}

	private String writeToHtml() {
		return FileManager.getInstance().writeToHtml(getHtmlText());
	}

	public void setIsTablePrint(boolean b) {
		setShowBorder(b);
		setShowHeader(b);
		setPrintWithOrd(b);
	}

	// The code below not be optimized. It should be deplicated soon!

	public void writeToHtmlWithMessageDialog(HedspiTable table) {
		String message = writeToHtml(table);
		if (message == null)
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Export to html success", "Export success",
					JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Export to html failed\nMessage: " + message,
					"Export failed", JOptionPane.ERROR_MESSAGE);
	}

	private String writeToHtml(HedspiTable table) {
		return FileManager.getInstance().writeToHtml(getHtmlText(table));
	}

	private String getHtmlText(HedspiTable table) {
		String ret = "<body>\n<center><h1>Hedspi student manager&copy;</h1></center><br>\n";

		ret += getTableHtml();
		ret += "<br>";
		ret += table.getTableHtml();

		// finish
		ret += "</body>";
		return ret;
	}
	// HERE: end of non-optimized code.
}
