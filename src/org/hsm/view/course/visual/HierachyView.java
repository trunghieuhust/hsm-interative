package org.hsm.view.course.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.hsm.control.Control;
import org.hsm.io.FileManager;
import org.hsm.model.hedspiObject.HedspiObject;
import org.hsm.model.hedspiObject.HedspiObjectWithNote;
import org.hsm.service.Pair;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class HierachyView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HedspiObject hedspiObject;
	private HierachyElement hierachyElement;

	/**
	 * Create the panel.
	 */
	public HierachyView() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnDraw = new JButton("Draw");
		btnDraw.setToolTipText("Refresh graphic");
		panel.add(btnDraw, "1, 1");

		JButton btnExport = new JButton("Export to png");
		btnExport.setToolTipText("Export to png image");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgBuff = new BufferedImage(hierachyElement
						.getWidth() + 10, hierachyElement.getHeight() + 10,
						BufferedImage.TYPE_INT_RGB);
				hierachyElement.paint(imgBuff.getGraphics());
				try {
					File outputFile = FileManager.getInstance()
							.getOutputFilePng();
					if (outputFile == null)
						return;
					ImageIO.write(imgBuff, "png", outputFile);
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(), "Export to file successed",
							"Export success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(Control.getInstance()
							.getMainWindow(),
							"Export error\nMessage: " + e1.getMessage(),
							"Export failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnExport, "3, 1");
		btnDraw.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");

		hierachyElement = new HierachyElement(null);
		scrollPane.setViewportView(hierachyElement);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (hedspiObject == null)
			return;
		ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>> background = (ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>>) Control
				.getInstance().getData("getBackgroundRelated", hedspiObject);
		if (background == null) {
			JOptionPane.showMessageDialog(
					Control.getInstance().getMainWindow(),
					"Get related backgrounds failed\nMessage: "
							+ Control.getInstance().getQueryMessage(),
					"Get data failed", JOptionPane.WARNING_MESSAGE);
			return;
		}

		HedspiObjectWithNote root = null;
		for (Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
			if (it.getT1().getId() == hedspiObject.getId()) {
				root = it.getT1();
				break;
			} else if (it.getT2().getId() == hedspiObject.getId()) {
				root = it.getT2();
				break;
			}
		if (root == null)
			return;

		HedspiObjectWithNote bak = root;
		do {
			bak = root;
			for (Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
				if (it.getT2().equals(root)) {
					root = it.getT1();
					break;
				}
		} while (!bak.equals(root));
		if (root == null)
			return;

		hierachyElement.draw(background, root);
	}

	public void setHedspiObject(HedspiObject hedspiObject) {
		this.hedspiObject = hedspiObject;
	}

}
