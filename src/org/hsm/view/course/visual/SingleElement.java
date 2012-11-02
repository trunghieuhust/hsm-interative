package org.hsm.view.course.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import org.hsm.model.hedspiObject.HedspiObjectWithNote;

public class SingleElement extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiObjectWithNote hedspiObject;
	private int cox;
	private int coy;
	private static int DEFAULT_WIDTH = 150;
	private static int DEFAULT_HEIGHT = 50;
	private static int DEFAULT_LINE_LEN = 50;
	private static Color DEFAULT_RECT_COLOR = Color.RED;
	private static Color DEFAULT_TEXT_COLOR = Color.BLUE;

	public SingleElement(HedspiObjectWithNote hedspiObject) {
		super();
		this.hedspiObject = hedspiObject;
		super.setVisible(true);
		super.setToolTipText(wrapText(hedspiObject.getNote()));
	}

	private String wrapText(String note) {
		String ret = "<html>";
		int cnt = 0;
		for (int i = 0; i < note.length(); i++)
			if (i > 0 && i % DEFAULT_LINE_LEN <= 5 && note.charAt(i) == ' '
					&& cnt >= 30) {
				ret += "<br>";
				cnt = 0;
			} else {
				ret += note.charAt(i);
				cnt++;
			}
		ret += "</html>";
		return ret;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Color bak = g.getColor();
		g.setColor(DEFAULT_RECT_COLOR);
		g.fillRect(0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1);
		g.setColor(DEFAULT_TEXT_COLOR);
		g.drawString(hedspiObject.getName(), 10, 20);
		g.setColor(bak);
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public int getWidth() {
		return DEFAULT_WIDTH;
	}

	public int getHeight() {
		return DEFAULT_HEIGHT;
	}

	public void setCoxy(int cox, int coy) {
		this.cox = cox;
		this.coy = coy;
	}

	public int getCox() {
		return cox;
	}

	public int getCoy() {
		return coy;
	}

	public int getInx() {
		return cox + getWidth() / 2;
	}

	public int getIny() {
		return coy;
	}

	public int getOutx() {
		return getInx();
	}

	public int getOuty() {
		return coy + getHeight();
	}
}
