//package org.hsm.view.course.visual;
//
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.util.ArrayList;
//
//import javax.swing.JComponent;
//
//import org.hsm.model.hedspiObject.HedspiObjectWithNote;
//import org.hsm.service.Pair;
//
//public class HierachyElementBackup extends JComponent {
//	
//	private static ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> background;
//	private HedspiObjectWithNote root;
//	private int input;
//	private static int DEFAULT_SPACE_H = 10;
//	private static int DEFAULT_SPACE_V = 100;
//	private int width;
//	private int height;
//
//	public HierachyElementBackup(HedspiObjectWithNote root) {
//		super();
//		super.setLayout(null);
//		super.setVisible(true);
//		this.root = root;
//	}
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private SingleElement rootElement;
//	private ArrayList<HierachyElementBackup> children;
//
//	public void draw(
//			ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> background, HedspiObjectWithNote root) {
//		HierachyElementBackup.background = background;
//		this.root = root;
//		super.removeAll();
//		refresh();
//		repaint();
//	}
//
//	public void refresh() {
//		children = new ArrayList<>();
//		for(Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
//			if (it.getT1().equals(root)){
//				HierachyElementBackup tmp = new HierachyElementBackup(it.getT2());
//				tmp.refresh();
//				children.add(tmp);
//			}
//		
//		rootElement = new SingleElement(root);
//		
//		//get size
//		width = 0;
//		height = 0;
//		for(HierachyElementBackup it : children){
//			width += it.getMyWidth() + DEFAULT_SPACE_H;
//			height = (int) Math.max(height, it.getMyHeight());
//		}
//		if (!children.isEmpty())
//			width -= DEFAULT_SPACE_H;
//		width = Math.max(width, SingleElement.DEFAULT_WIDTH);
//		height += DEFAULT_SPACE_V + SingleElement.DEFAULT_HEIGHT;
//		if (children.isEmpty())
//			height -= DEFAULT_SPACE_V;
//	}
//	
//	public void paint(Graphics g) {
//		super.paint(g);
//		if (background == null || root == null)
//			return;
//		//draw all
//		int x = (int)((getMyWidth() + 1)/2);
//		rootElement.setBounds(x - (int)((SingleElement.DEFAULT_WIDTH  + 1)/ 2), 0, SingleElement.DEFAULT_WIDTH, SingleElement.DEFAULT_HEIGHT);
//		input = x;
//		super.add(rootElement);
//		rootElement.setVisible(true);
//		rootElement.repaint();
//		
//		x = 0;
//		for(HierachyElementBackup it : children){
//			int y = SingleElement.DEFAULT_HEIGHT + DEFAULT_SPACE_V;
//			it.setBounds(x, y, it.getMyWidth(), it.getMyHeight());
//			super.add(it);
//			it.repaint();
//			//connect
//			if (g != null)
//			g.drawLine((int)((getMyWidth() + 1)/2) , SingleElement.DEFAULT_HEIGHT, x + (int)((it.getMyWidth() + 1) / 2), y);
//			x += it.getMyWidth() + DEFAULT_SPACE_H;
//		}
//	}
//
//	public int getInput() {
//		return input;
//	}
//
//	public int getMyWidth() {
//		return width;
//	}
//
//	public int getMyHeight() {
//		return height;
//	}
//	
//	@Override
//	public Dimension getPreferredSize(){
//		return new Dimension(width, height);
//	}
// }
