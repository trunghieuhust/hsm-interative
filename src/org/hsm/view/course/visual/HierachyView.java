package org.hsm.view.course.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.hsm.control.Control;
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
				ColumnSpec.decode("center:default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JButton btnDraw = new JButton("Draw");
		btnDraw.addActionListener(this);
		add(btnDraw, "2, 2");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");
		
		hierachyElement = new HierachyElement(null);
		scrollPane.setViewportView(hierachyElement);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (hedspiObject == null)
			return;
		ArrayList<Pair<HedspiObjectWithNote,HedspiObjectWithNote>> background = (ArrayList<Pair<HedspiObjectWithNote, HedspiObjectWithNote>>) Control.getInstance().getData("getBackgroundRelated", hedspiObject);
		if (background == null){
			JOptionPane.showMessageDialog(null, "Get related backgrounds failed", "Get data failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		HedspiObjectWithNote root = null;
		for(Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
			if (it.getT1().getId() == hedspiObject.getId()){
				root = it.getT1();
				break;
			} else if (it.getT2().getId() == hedspiObject.getId()){
				root = it.getT2();
				break;
			}
		if (root == null)
			return;
		
		HedspiObjectWithNote bak = root;
		do{
			bak = root;
			for(Pair<HedspiObjectWithNote, HedspiObjectWithNote> it : background)
				if (it.getT2().equals(root)){
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
