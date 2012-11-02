package org.hsm.view.course.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

import org.hsm.model.hedspiObject.HedspiObjectWithNote;
import org.hsm.service.Pair;

public class HierachyElement extends JComponent {

	private static ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> background;
	private static HashMap<HedspiObjectWithNote, SingleElement> allElements;
	private static int DEFAULT_SPACE_H = 10;
	private static int DEFAULT_SPACE_V = 100;
	private int width;
	private int height;
	private static int ARROW_LEN = 10;
	private static Color DEFAULT_ARROW_COLOR = Color.ORANGE;
	private static Color DEFAULT_FOREGROUND_COLOR = Color.GREEN;

	public HierachyElement(HedspiObjectWithNote root) {
		super();
		super.setLayout(null);
		super.setForeground(DEFAULT_FOREGROUND_COLOR);
		if (root != null)
			refresh(root);
	}

	private static final long serialVersionUID = 1L;
	private SingleElement rootElement;
	private ArrayList<HierachyElement> children;

	public void draw(
			ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> background,
			HedspiObjectWithNote root) {
		HierachyElement.background = background;
		super.removeAll();
		if (allElements == null)
			allElements = new HashMap<>();
		allElements.clear();
		refresh(root);
		setCoxy(0, 0);
		this.setVisible(true);
		repaint();
	}

	public void setCoxy(int cox, int coy) {
		// draw all
		int x = (int) (width / 2);
		rootElement.setCoxy(cox + x - (int) (rootElement.getWidth() / 2), coy);

		x = 0;
		int y = rootElement.getHeight() + DEFAULT_SPACE_V;
		for (HierachyElement it : children) {
			it.setCoxy(cox + x, coy + y);
			x += it.getWidth() + DEFAULT_SPACE_H;
		}
	}

	public void refresh(HedspiObjectWithNote root) {
		rootElement = new SingleElement(root);
		allElements.put(root, rootElement);

		children = new ArrayList<>();
		for (Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
			if (it.getT1().equals(root)) {
				HedspiObjectWithNote child = it.getT2();
				if (!allElements.containsKey(child)) {
					HierachyElement tmp = new HierachyElement(child);
					children.add(tmp);
				}
			}

		// get size
		width = 0;
		height = 0;
		for (HierachyElement it : children) {
			width += it.getWidth() + DEFAULT_SPACE_H;
			height = (int) Math.max(height, it.getHeight());
		}
		if (!children.isEmpty())
			width -= DEFAULT_SPACE_H;
		width = Math.max(width, rootElement.getWidth());
		height += DEFAULT_SPACE_V + rootElement.getHeight();
		if (children.isEmpty())
			height -= DEFAULT_SPACE_V;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (background == null)
			return;
		for (Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background) {
			HedspiObjectWithNote parent = it.getT1();
			HedspiObjectWithNote child = it.getT2();

			SingleElement p = allElements.get(parent);
			SingleElement c = allElements.get(child);
			if (p == null || c == null)
				continue;
			connect(g, p, c);
		}
		for (SingleElement it : allElements.values()) {
			it.setBounds(it.getCox(), it.getCoy(), it.getWidth(),
					it.getHeight());
			super.add(it);
			it.setVisible(true);
			it.repaint();
		}
	}

	private void connect(Graphics g, SingleElement p, SingleElement c) {
		drawArrow(g, p.getOutx(), p.getOuty(), c.getInx(), c.getIny());
	}

	private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
		Color bak = g.getColor();
		g.setColor(DEFAULT_ARROW_COLOR);
		g.drawLine(x1, y1, x2, y2);
		g.fillArc(x2 - ARROW_LEN, y2 - ARROW_LEN, ARROW_LEN * 2, ARROW_LEN * 2,
				-30 - (int) Math.toDegrees(Math.atan2(y1 - y2, x1 - x2)), 60);
		g.setColor(bak);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
