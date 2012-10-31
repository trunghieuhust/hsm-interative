package org.hsm.view.about;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.hsm.control.Control;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;

	private BufferedImage image;

	/**
	 * Create the panel.
	 */
	public ImagePanel(String fileName) {
		setSize(new Dimension(300, 300));
		setLayout(new FormLayout(new ColumnSpec[] {}, new RowSpec[] {}));
		this.fileName = fileName;
		image = null;
		addImage();
	}

	private void addImage() {
		if (fileName == null)
			return;
		if (fileName.equals(""))
			return;
		try {
			File input = new File(fileName);
			image = ImageIO.read(input);
		} catch (IOException ie) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, ie.getMessage());
			return;
		}
	}

	@Override
	public void paint(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, 100, 100, null);
	}
}
