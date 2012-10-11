package org.hsm.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hsm.model.hedspiObject.HedspiObject;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.ListSelectionModel;

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
	public ObjectListPane(){
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblClassList = DefaultComponentFactory.getInstance().createLabel("Objects list");
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
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnNew = new JButton("New");
		panel.add(btnNew, "2, 2");
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HedspiObject value = newElement();
				if (value != null){
					model.addElement(value);
					list.setSelectedValue(value, true);
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
				if (message == null){
					model.removeElement(value);
					list.clearSelection();
				} else
					JOptionPane.showMessageDialog(null, "Delete failed.\nMessage: " + message, "Delete failed", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HedspiObject[] arr = getRefresh();
				if (arr == null)
					return;
				Arrays.sort(arr);
				model.clear();
				for(HedspiObject it : arr)
					model.addElement(it);
			}
		});
		panel.add(btnRefresh, "6, 2");
		
		refresh();
	}
	
	public ObjectListPane(String label){
		this();
		lblClassList.setText(label);
	}
	

	/**
	 * Hàm này sẽ được gọi khi đối tượng được chọn thay đổi và khác null.
	 * Tức khi gọi hàm đã luôn đảm bảo value != null.
	 * @param value đối tượng được chọn, đảm bảo khác null.
	 */
	abstract void selectValue(HedspiObject value);
	/**
	 * Trả về đối tượng mới thêm vào, nếu lấy về không thành công thì trả về null.
	 * Phần tử null sẽ không được thêm vào danh sách.
	 * Phần tử khác null sẽ được thêm vào.
	 * @return giá trị phần tử mới. null nếu không thành công.
	 */
	abstract HedspiObject newElement();
	/**
	 * Hàm này được gọi khi người dùng xóa đối tượng.
	 * @param value khi gọi hàm này đã đảm bảo cho value != null
	 * @return trả về null nếu thành công. Ngược lại, hãy trả về thông báo lỗi.
	 */
	abstract String removeElement(HedspiObject value);
	/**
	 * Reload CSDL. Ngoài ra, hàm này cũng được gọi khi khởi tạo instance.
	 * @return danh sách các objects.
	 */
	abstract HedspiObject[] getRefresh();


	public void refresh() {
		btnRefresh.doClick();
	}
}
