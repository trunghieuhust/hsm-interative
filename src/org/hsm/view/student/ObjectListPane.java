package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hsm.model.hedspiObject.HedspiObject;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public abstract class ObjectListPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<HedspiObject> model;
	private JList<HedspiObject> list;
	private JButton btnRefresh;
	private JLabel lblClassList;

	/**
	 * Create the panel.
	 */
	public ObjectListPane() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		lblClassList = DefaultComponentFactory.getInstance().createLabel(
				"Objects list");
		add(lblClassList, "2, 2");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, fill, fill");

		model = new DefaultListModel<>();
		list = new JList<HedspiObject>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				HedspiObject value = list.getSelectedValue();
				if (value != null)
					selectValue(value);
			}
		});
		scrollPane.setViewportView(list);

		JPanel panel = new JPanel();
		add(panel, "2, 6, center, fill");
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnNew = new JButton("New");
		panel.add(btnNew, "2, 2");
		btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HedspiObject value = newElement();
				if (value != null) {
					model.addElement(value);
					list.setSelectedValue(value, true);
				} else {
					JOptionPane.showMessageDialog(null,
							"Failed to create new element",
							"Failed to create new element",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnRemove = new JButton("Remove");
		panel.add(btnRemove, "4, 2");
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HedspiObject value = list.getSelectedValue();
				if (value == null)
					return;
				String message = removeElement(value);
				if (message == null) {
					model.removeElement(value);
					list.clearSelection();
				} else
					JOptionPane.showMessageDialog(null,
							"Delete failed.\nMessage: " + message,
							"Delete failed", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HedspiObject[] arr = getRefresh();
				if (arr == null) {
					JOptionPane.showConfirmDialog(null,
							"Cannot get list of objects", "Refresh failed",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				model.clear();
				for (HedspiObject it : arr)
					model.addElement(it);
			}
		});
		panel.add(btnRefresh, "6, 2");

		refresh();
	}

	public ObjectListPane(String label) {
		this();
		lblClassList.setText(label);
	}

	/**
	 * Hàm này sẽ được gọi khi đối tượng được chọn thay đổi và khác
	 * <code>null</code>. Tức khi gọi hàm đã luôn đảm bảo
	 * <code>value != null</code>.
	 * 
	 * @param value
	 *            đối tượng được chọn, đảm bảo khác <code>null</code>.
	 */
	public abstract void selectValue(HedspiObject value);

	/**
	 * Trả về đối tượng mới thêm vào, nếu lấy về không thành công thì trả về
	 * <code>null</code>. Phần tử <code>null</code> sẽ không được thêm vào danh
	 * sách. Phần tử khác <code>null</code> sẽ được thêm vào.
	 * 
	 * @return giá trị phần tử mới. <code>null</code> nếu không thành công.
	 */
	public abstract HedspiObject newElement();

	/**
	 * Hàm này được gọi khi người dùng xóa đối tượng.
	 * 
	 * @param value
	 *            khi gọi hàm này đã đảm bảo cho <code>value != null</code>
	 * @return trả về <code>null</code> nếu thành công. Ngược lại, hãy trả về
	 *         thông báo lỗi.
	 */
	public abstract String removeElement(HedspiObject value);

	/**
	 * Reload CSDL. Ngoài ra, hàm này cũng được gọi khi
	 * {@link #ObjectListPane() khởi tạo} instance.
	 * 
	 * @return danh sách các objects, nếu lỗi thì trả về <code>null</code> sẽ có
	 *         thông báo hiện lên và danh sách không được cập nhật vào hiển thị.
	 *         Lưu ý rằng <code>null</code> khác với danh sách rỗng.
	 */
	public abstract HedspiObject[] getRefresh();

	public void refresh() {
		btnRefresh.doClick();
	}
}
