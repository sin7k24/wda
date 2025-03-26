package com.oneitthing.wda.view.diff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DiffDialog extends JDialog {
	private JTable table;
	private JTable table_1;
	private DefaultTableModel model;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiffDialog dialog = new DiffDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DiffDialog() {
		setSize(new Dimension(1024, 768));
		setTitle("比較ビュー");

		
		final JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setShowHorizontalLines(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setShowHorizontalLines(false);

		model = createModel();
		table.setModel(model);
		table_1.setModel(model);

		table_1.setAutoCreateRowSorter(true);
		table.setRowSorter(table_1.getRowSorter());
		table.setSelectionModel(table_1.getSelectionModel());

		scrollPane.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL) {
			@Override
			public Dimension getPreferredSize() {
				Dimension dim = super.getPreferredSize();
				return new Dimension(0, dim.height);
			}
		});
		scrollPane_1.getVerticalScrollBar().setModel(
				scrollPane.getVerticalScrollBar().getModel());
		
		table.removeColumn(table.getColumnModel().getColumn(2));
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setMaxWidth(40);
		
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));

		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(true));
		table_1.setDefaultRenderer(Object.class, new CustomTableCellRenderer(false));
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				splitPane.setDividerLocation(0.5);
			}
		});

	}
	
	public class CustomTableCellRenderer extends DefaultTableCellRenderer {
		public Color numColor = new Color(255,255,255,64);
		public Color unmatchColor = new Color(255,0,0,160);
		public Color insertColor = new Color(0,255,0,160);
		public Color deleteColor = new Color(0,0,255,160);
		
		public boolean left;
		
		public CustomTableCellRenderer(boolean left) {
			this.left = left;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String original = (String) model.getValueAt(row, 1);
			String revised = (String) model.getValueAt(row, 2);


			if (!original.equals(revised)) {
				c.setBackground(unmatchColor);
				if(original.length() == 0) {
					c.setBackground(deleteColor);
				}else if(revised.length() == 0) {
					c.setBackground(insertColor);
				}
			} else {
				c.setBackground(table.getBackground());
			}
			c.setForeground(Color.WHITE);
			
			if(left && column == 0) {
				c.setBackground(numColor);
				c.setForeground(Color.BLACK);
			}

			return c;
		}
	}
	
	public DefaultTableModel createModel() {
		String[] columnNames = { "NO.", "original", "revised" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		
		return model;
	}
	
	public void addSourceRow(String[] row) {
		model.addRow(row);
	}

}
