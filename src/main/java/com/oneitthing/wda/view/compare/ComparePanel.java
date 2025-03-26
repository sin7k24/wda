package com.oneitthing.wda.view.compare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ComparePanel extends JPanel {
	private JTable table;
	private JTable table_1;
	DefaultTableModel model;
	private JLabel label_1;

	public int errorNum;

	private List<Integer> errorRows = new ArrayList<Integer>();
	private ListIterator<Integer> errorIterator = errorRows.listIterator();
	
	private int currentErrorRow;
	
	JButton button;
	JButton button_1;
	
	/**
	 * Create the panel.
	 */
	public ComparePanel() {
		setName("comparePanel");
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));

		final JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setName("comparePanel.jtLeft");
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setName("");
		splitPane.setRightComponent(scrollPane_1);

		table_1 = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_1.setName("comparePanel.jtRight");
		scrollPane_1.setViewportView(table_1);

		scrollPane.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL) {
			@Override
			public Dimension getPreferredSize() {
				Dimension dim = super.getPreferredSize();
				return new Dimension(0, dim.height);
			}
		});
		scrollPane_1.getVerticalScrollBar().setModel(
				scrollPane.getVerticalScrollBar().getModel());

		DefaultTableModel model = createModel();
		table.setModel(model);
		table_1.setModel(model);

		table_1.setAutoCreateRowSorter(true);
		table.setRowSorter(table_1.getRowSorter());
		table.setSelectionModel(table_1.getSelectionModel());
		EventQueue.invokeLater(new Runnable() {
			  @Override public void run() {
					splitPane.setDividerLocation(0.5);
			  }
			});
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		
		JLabel label = new JLabel("差異検出数：");
		panel_1.add(label);
		
		label_1 = new JLabel("");
		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		
		button = new JButton("<<");
		button.setName("comparePanel.jbPrevError");
		panel_2.add(button);
		
		button_1 = new JButton(">>");
		button_1.setName("comparePanel.jbNextError");
		panel_2.add(button_1);

		table.removeColumn(table.getColumnModel().getColumn(4));
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.removeColumn(table.getColumnModel().getColumn(4));
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setMaxWidth(40);
		col = table.getColumnModel().getColumn(2);
		col.setMaxWidth(100);
		col = table.getColumnModel().getColumn(3);
		col.setMaxWidth(60);
		
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		col = table_1.getColumnModel().getColumn(1);
		col.setMaxWidth(100);
		col = table_1.getColumnModel().getColumn(2);
		col.setMaxWidth(60);
		
		CustomTableCellRenderer leftRenderer = new CustomTableCellRenderer(true);
		table.setDefaultRenderer(Object.class, leftRenderer);

		CustomTableCellRenderer rightRenderer = new CustomTableCellRenderer(false);
		table_1.setDefaultRenderer(Object.class, rightRenderer);


	}

	public DefaultTableModel createModel() {
		String[] columnNames = { "NO.", "パス", "MD5(B64)", "javac ver", "パス", "MD5(B64)", "javac ver" };
		model = new DefaultTableModel(columnNames, 0);

//		String[][] tabledata = {
//				{ "ioss/account.html", "1,914", "ioss/account.html", "1,914" },
//				{ "ioss/accountManagement.html", "2,129",
//						"ioss/accountManagement.html", "2,129" },
//				{ "ioss/accountUpdate.html", "1,112",
//						"ioss/accountUpdate.html", "1,112" },
//				{ "ioss/billing.html", "1,423", "ioss/billing.html", "1,423" },
//				{ "ioss/bluemap.html", "1,924", "ioss/bluemap.html", "1,924" },
//				{ "ioss/blueMapUsageStatus.html", "3,491",
//						"ioss/blueMapUsageStatus.html", "3,491" },
//				{ "ioss/contact.html", "481", "", "" },
//				{ "ioss/corporation.html", "2,333", "ioss/corporation.html",
//						"2,333" },
//				{
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.class",
//						"303",
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.class",
//						"303" },
//				{
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.java",
//						"70",
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.java",
//						"71" },
//				{ "testdatafile1", "0", "testdatafile1", "0" },
//				{ "testdatafile2", "0", "testdatafile2", "0" },
//				{ "testdatafile3", "0", "testdatafile3", "0" },
//				{ "testdatafile4", "0", "testdatafile4", "0" },
//				{ "testdatafile5", "1", "testdatafile5", "0" },
//				{ "testdatafile6", "0", "testdatafile6", "0" },
//				{ "testdatafile7", "0", "testdatafile7", "0" },
//				{ "testdatafile8", "0", "testdatafile8", "0" },
//				{ "testdatafile9", "0", "testdatafile9", "0" },
//				{ "testdatafile10", "0", "testdatafile10", "0" },
//				{ "testdatafile11", "0", "testdatafile11", "0" },
//				{ "testdatafile12", "0", "testdatafile12", "0" },
//				{ "testdatafile13", "0", "testdatafile13", "0" },
//				{ "testdatafile14", "0", "testdatafile14", "0" },
//				{ "testdatafile15", "0", "testdatafile15", "0" },
//				{ "testdatafile16", "0", "testdatafile16", "0" },
//				{ "testdatafile17", "0", "testdatafile17", "0" },
//				{ "testdatafile18", "0", "testdatafile18", "0" },
//				{ "testdatafile19", "0", "testdatafile19", "0" },
//				{ "testdatafile20", "0", "testdatafile20", "0" },
//				{
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.java",
//						"70",
//						"ioss/WEB-INF/classes/jp/co/fujitsu/ioss/base/BaseEntity.java",
//						"70" }};
//		for (int i = 0; i < tabledata.length; i++) {
//			model.addRow(tabledata[i]);
//		}
		return model;

	}

	public class CustomTableCellRenderer extends DefaultTableCellRenderer {
		public Color numColor = new Color(255,255,255,64);
		public Color errColor = new Color(255,0,0,160);
		
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
			String path1 = (String) model.getValueAt(row, 1);
			String path2 = (String) model.getValueAt(row, 4);

			String hash1 = (String) model.getValueAt(row, 2);
			String hash2 = (String) model.getValueAt(row, 5);

			String javacver1 = (String) model.getValueAt(row, 3);
			String javacver2 = (String) model.getValueAt(row, 6);

			if (!path1.equals(path2)
					|| !hash1.equals(hash2)
					|| !javacver1.equals(javacver2)
					) 
			{
				c.setBackground(errColor);
				setErrorNum(errorNum);
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
	
	public void setErrorNum(int errorNum) {
		label_1.setText(String.valueOf(errorNum));
	}
	
	public void addCompareRow(final String[] row) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				model.addRow(row);

				if(!row[1].equals(row[4])
					|| !row[2].equals(row[5])
					|| !row[3].equals(row[6]))
				{ 
					errorNum++;
					setErrorNum(errorNum);
					System.out.println("errorRowNum = " + (model.getRowCount()-1));
					errorRows.add(model.getRowCount()-1);

				}
				
				int i = table.convertRowIndexToView(model.getRowCount()-1);
				Rectangle r = table.getCellRect(i, 0, true);
				table.scrollRectToVisible(r);
				
				errorIterator = errorRows.listIterator();
			}
		});
	}
	
	public void jumpToPrevError() {
		if(errorIterator.hasPrevious()) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					button_1.setEnabled(true);
					
					int prevRow = errorIterator.previous();
					if(prevRow == currentErrorRow) {
						prevRow = errorIterator.previous();
					}
					currentErrorRow = prevRow;
					
					int i = table.convertRowIndexToView(prevRow);
					Rectangle r = table.getCellRect(i, 0, true);
					table.scrollRectToVisible(r);
				}
			});
		}else{
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					button.setEnabled(false);
				}
			});
		}
	}
	
	public void jumpToNextError() {
		if(errorIterator.hasNext()) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					button.setEnabled(true);
					
					int nextRow = errorIterator.next();
					if(nextRow == currentErrorRow) {
						nextRow = errorIterator.next();
					}
					currentErrorRow = nextRow;
					
					int i = table.convertRowIndexToView(nextRow);
					Rectangle r = table.getCellRect(i, 0, true);
					table.scrollRectToVisible(r);
				}
			});
		}else{
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					button_1.setEnabled(false);
				}
			});
		}
	}

}
