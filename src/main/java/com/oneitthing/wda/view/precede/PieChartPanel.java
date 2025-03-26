package com.oneitthing.wda.view.precede;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class PieChartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField jtfInputPath;
	private JTextField jtfWorkSpacePath;
	private JLabel lblDetectFileNum;
	private JLabel lblDetectDirNum;
	private JFreeChart chart;
	
	/**
	 * Create the panel.
	 */
	public PieChartPanel() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));

		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());


		chart = ChartFactory.createPieChart3D("", null, false, false, false);
//		TextTitle textTitle = new TextTitle("オリジナルaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		textTitle.setPaint(Color.WHITE);
//		textTitle.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 18));
//		chart.setTitle(textTitle);

		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("データ生成中");
		plot.setBackgroundAlpha(0.1f);
		plot.setForegroundAlpha(0.6f);

//		plot.setSimpleLabels(true);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}"));
//		plot.setLabelBackgroundPaint(null);
//		plot.setLabelOutlineStroke(null);
		plot.setLabelShadowPaint(null);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setSize(200, 200);
		add(chartPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(50, 100));
		panel.setMinimumSize(new Dimension(50, 50));
		panel.setBounds(new Rectangle(0, 0, 50, 50));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel label = new JLabel("入力パス：");
		panel.add(label, "2, 2, right, default");
		
		jtfInputPath = new JTextField();
		jtfInputPath.setName("pieChartPanel.jtfInputPath");
		panel.add(jtfInputPath, "4, 2, fill, default");
		jtfInputPath.setColumns(10);
		
		JLabel label_1 = new JLabel("作業パス：");
		panel.add(label_1, "2, 4, right, default");
		
		jtfWorkSpacePath = new JTextField();
		panel.add(jtfWorkSpacePath, "4, 4, fill, default");
		jtfWorkSpacePath.setColumns(10);
		
		JLabel label_2 = new JLabel("検出ファイル数：");
		panel.add(label_2, "2, 6, right, default");
		
		lblDetectFileNum = new JLabel("");
		panel.add(lblDetectFileNum, "4, 6");
		
		JLabel label_3 = new JLabel("検出ディレクトリ数：");
		panel.add(label_3, "2, 8");
		
		lblDetectDirNum = new JLabel("");
		panel.add(lblDetectDirNum, "4, 8");
	}

	public void setInputPath(String inputPath) {
		jtfInputPath.setText(inputPath);
	}

	public String getInputPath() {
		return jtfInputPath.getText();
	}

	public void setWorkSpacePath(String workSpacePath) {
		jtfWorkSpacePath.setText(workSpacePath);
	}
	
	public String getWorkSpacePath() {
		return jtfWorkSpacePath.getText();
	}
	
	
	public void setDetectFileNum(String detectFileNum) {
		lblDetectFileNum.setText(detectFileNum);
	}

	public void setDetectDirNum(String detectDirNum) {
		lblDetectDirNum.setText(detectDirNum);
	}

	public void setChartData(Map<String, Integer> fileRetio) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		Set<Entry<String, Integer>> set = fileRetio.entrySet();
		Iterator<Entry<String, Integer>> it = set.iterator();
		
		while(it.hasNext()) {
			Entry<String, Integer> entry = it.next();
			String key = entry.getKey();
			Integer value = entry.getValue();
			dataset.setValue(key, value);
		}
		
		((PiePlot)chart.getPlot()).setDataset(dataset);
	}
}
