package org.hsm.view.option;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.hsm.control.Control;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class OptionPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel<UI_TYPE> modelUI;
	private Properties prop;
	private static final String UI_KEY = "UI";
	protected static final String PROP_FILE = "hsm.prop";
	private JComboBox<UI_TYPE> comboBoxUI;

	private static enum UI_TYPE {
		DEFAULT, SYSTEM, CROSS_PLATFORM
	};

	/**
	 * Create the panel.
	 */
	public OptionPane() {
		prop = new Properties();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JPanel panel = new JPanel();
		add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblUi = new JLabel("UI");
		panel.add(lblUi, "1, 1, right, default");
		comboBoxUI = new JComboBox<>();
		lblUi.setLabelFor(comboBoxUI);

		panel.add(comboBoxUI, "3, 1, fill, default");
		modelUI = new DefaultComboBoxModel<UI_TYPE>(UI_TYPE.values());
		comboBoxUI.setModel(modelUI);
		comboBoxUI.setSelectedIndex(0);
		comboBoxUI.setToolTipText("Set UI for application");
		comboBoxUI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				UI_TYPE uitype = (UI_TYPE) comboBoxUI.getSelectedItem();
				switch (uitype) {
				case DEFAULT:
					prop.setProperty(UI_KEY, "DEFAULT");
					break;
				case CROSS_PLATFORM:
					prop.setProperty(UI_KEY, "CROSS_PLATFORM");
					break;
				case SYSTEM:
					prop.setProperty(UI_KEY, "SYSTEM");
					break;
				default:
					break;
				}
			}
		});

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 4, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					prop.store(new FileOutputStream(PROP_FILE),
							"Property file for Hedspi Student Manager client application");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog((JFrame) Control
							.getInstance().getData("getOptionFrame"),
							"Apply failed\nMessage: " + e1.getMessage(),
							"Apply failed", JOptionPane.WARNING_MESSAGE);
					Control.getInstance()
							.getLogger()
							.log(Level.WARNING,
									"Write option file failed. Message: "
											+ e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog((JFrame) Control.getInstance()
						.getData("getOptionFrame"),
						"Apply ok. Refresh for view UI change", "Set UI ok",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_1.add(btnApply, "1, 1");
		btnApply.setToolTipText("Apply and save options");
		btnApply.setMnemonic('a');
		modelUI = new DefaultComboBoxModel<UI_TYPE>(UI_TYPE.values());
	}

	public static void setUI() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROP_FILE));
		} catch (IOException e) {
			Control.getInstance()
					.getLogger()
					.log(Level.INFO,
							"Load properties file error. Skip scanning\nMessage: "
									+ e.getMessage());
			return;
		}

		try {
			String uiName = prop.getProperty(UI_KEY);
			if (uiName == null)
				return;
			switch (uiName) {
			case "DEFAULT":
				UIManager.setLookAndFeel((LookAndFeel) null);
				break;
			case "CROSS_PLATFORM":
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				break;
			case "SYSTEM":
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				break;
			default:
				break;
			}
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException
				| InstantiationException | IllegalAccessException e1) {
			JOptionPane.showMessageDialog(
					(JFrame) Control.getInstance().getData("getOptionFrame"),
					"Set UI for application failed\nMessage: "
							+ e1.getMessage(), "Set UI failed",
					JOptionPane.WARNING_MESSAGE);
			Control.getInstance()
					.getLogger()
					.log(Level.WARNING,
							"Set UI for application failed. Message: "
									+ e1.getMessage());
			return;
		}
	}
}
