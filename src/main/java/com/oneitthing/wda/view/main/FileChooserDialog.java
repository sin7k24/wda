package com.oneitthing.wda.view.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FileChooserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDjobkhoiosswar;
	private JTextField txtDjobkhoiosswar_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FileChooserDialog dialog = new FileChooserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FileChooserDialog() {
		setModal(true);
		setName("fileChooserDialog");
		setTitle("差異解析するファイルまたはディレクトリを選択してください");
		setBounds(100, 100, 450, 186);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel label = new JLabel("入力１：");
			contentPanel.add(label, "2, 2, right, default");
		}
		{
			txtDjobkhoiosswar = new JTextField();
			txtDjobkhoiosswar.setName("fileChooserDialog.jtfInput1");
			contentPanel.add(txtDjobkhoiosswar, "4, 2, fill, default");
			txtDjobkhoiosswar.setColumns(10);
		}
		{
			JButton btnNewButton = new JButton("参照");
			btnNewButton.setName("fileChooserDialog.jbRef1");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			contentPanel.add(btnNewButton, "6, 2");
		}
		{
			JLabel label = new JLabel("入力２：");
			contentPanel.add(label, "2, 4, right, default");
		}
		{
			txtDjobkhoiosswar_1 = new JTextField();
			txtDjobkhoiosswar_1.setName("fileChooserDialog.jtfInput2");
			contentPanel.add(txtDjobkhoiosswar_1, "4, 4, fill, default");
			txtDjobkhoiosswar_1.setColumns(10);
		}
		{
			JButton button = new JButton("参照");
			button.setName("fileChooserDialog.jbRef2");
			contentPanel.add(button, "6, 4");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setName("jbPrecedeStart");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setName("fileChooserDialog.jbPrecedeCancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel.setBorder(new TitledBorder(null, "option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				JCheckBox chckbxNewCheckBox = new JCheckBox("再帰的にjarファイルを逆コンパイルする");
				chckbxNewCheckBox.setName("fileChooserDialog.jckCyclicDecompile");
				panel.add(chckbxNewCheckBox);
			}
		}
	}

}
