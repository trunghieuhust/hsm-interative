package org.hsm.view.course;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Test extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Test() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("157px"),
				ColumnSpec.decode("136px"),},
			new RowSpec[] {
				com.jgoodies.forms.factories.FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("15px"),}));
		JLabel label = DefaultComponentFactory.getInstance().createLabel("New JGoodies label");
		add(label, "2, 2, left, top");
	}

}
