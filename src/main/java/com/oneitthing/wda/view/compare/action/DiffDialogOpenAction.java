package com.oneitthing.wda.view.compare.action;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.Model;
import com.oneitthing.wda.model.CompareByListModel;
import com.oneitthing.wda.view.compare.ComparePanel;
import com.oneitthing.wda.view.diff.DiffDialog;
import com.oneitthing.wda.view.precede.PrecedePanel;

import difflib.DiffRow;
import difflib.DiffRowGenerator;

public class DiffDialogOpenAction extends BaseAction {

	@Override
	protected boolean prepare(ParameterMapping parameterMapping)
			throws Exception {
		MouseEvent me = ((MouseEvent) parameterMapping.getEventObject());

		if (me.getClickCount() != 2) {
			return false;
		}

		// DiffDialog diffDialog = new DiffDialog();
		// showWindow(diffDialog, false);

		Point pt = me.getPoint();
		JTable table = (JTable) parameterMapping.getEventSource();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int idx = table.rowAtPoint(pt);
		if (idx >= 0) {
			int row = table.convertRowIndexToModel(idx);
			
			PrecedePanel precedePanel = (PrecedePanel) getComponent("precedePanel");
			
			List<String> original = null;
			List<String> revised = null;
			if(!"".equals(model.getValueAt(row, 1))) {
				String leftFilePath = 
						precedePanel.getOriginalExtractPath() + File.separator + model.getValueAt(row, 1);
				original = fileToLines(leftFilePath);
			}else{
				original = new ArrayList<String>();
			}

			if(!"".equals(model.getValueAt(row, 4))) {
				String rightFilePath = 
						precedePanel.getRevisedExtractPath() + File.separator + model.getValueAt(row, 4);
				revised = fileToLines(rightFilePath);
			}else{
				revised = new ArrayList<String>();
			}
			

			DiffDialog diffDialog = new DiffDialog();

			DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
			builder.showInlineDiffs(true);
			DiffRowGenerator generator = builder.build();
			List<DiffRow> diffRows = generator.generateDiffRows(original, revised);

			int lineCount = 0;
			for (DiffRow diffRow : diffRows) {
				lineCount++;
				if (diffRow.getTag().equals(DiffRow.Tag.INSERT)) {
					String newLine = diffRow.getNewLine();

					String[] sourceRow = new String[] {String.valueOf(lineCount), "", newLine };
					diffDialog.addSourceRow(sourceRow);
				} else if (diffRow.getTag().equals(DiffRow.Tag.DELETE)) {
					String oldLine = diffRow.getOldLine();

					String[] sourceRow = new String[] {String.valueOf(lineCount), oldLine, "" };
					diffDialog.addSourceRow(sourceRow);

				} else if (diffRow.getTag().equals(DiffRow.Tag.CHANGE)) {
					String oldLine = diffRow.getOldLine();
					String newLine = diffRow.getNewLine();

					String[] sourceRow = new String[] {
							String.valueOf(lineCount), oldLine, newLine };
					diffDialog.addSourceRow(sourceRow);
				} else if (diffRow.getTag().equals(DiffRow.Tag.EQUAL)) {
					String oldLine = diffRow.getOldLine();
					String newLine = diffRow.getNewLine();

					String[] sourceRow = new String[] {
							String.valueOf(lineCount), newLine, oldLine };
					diffDialog.addSourceRow(sourceRow);
				}
			}
			diffDialog.setLocationRelativeTo((Component)getComponent("mainFrame"));
			diffDialog.setVisible(true);
		}

		return false;
	}

	@Override
	protected void reserveModels(List<Class<? extends Model>> models) {
		models.add(CompareByListModel.class);
	}

	@Override
	public boolean nextModel(int index, ModelProcessEvent prev, Model next)
			throws Exception {
		System.out.println("nextModel index = " + index);
		if (index == 0) {
			PrecedePanel precedePanel = (PrecedePanel) getComponent("precedePanel");
			List<String> originalFiles = precedePanel.getOriginalFiles();
			List<String> revisedFiles = precedePanel.getRevisedFiles();
			String originalExtractPath = precedePanel.getOriginalExtractPath();
			String revisedExtractPath = precedePanel.getRevisedExtractPath();

			ComparePanel comparePanel = (ComparePanel) getComponent("comparePanel");

			((CompareByListModel) next).setOriginalFiles(originalFiles);
			((CompareByListModel) next).setRevisedFiles(revisedFiles);
			((CompareByListModel) next)
					.setOriginalWorkspacePath(originalExtractPath);
			((CompareByListModel) next)
					.setRevisedWorkspacePath(revisedExtractPath);
			((CompareByListModel) next).setComparePanel(comparePanel);

			((CompareByListModel) next).setAsync(true);
		}

		return true;
	}

	@Override
	public void successForward(int index, Model model, Object result)
			throws Exception {
		System.out.println(index + "success");

		if (index == 0) {
		}
	}

	@Override
	public void complete(ParameterMapping parameterMapping) throws Exception {
		System.out.println("complete");

		ComparePanel comparePanel = (ComparePanel) getComponent("comparePanel");
		comparePanel.setErrorNum(comparePanel.errorNum);
	}
	
    private static List<String> fileToLines(String filename) {
        List<String> lines = new LinkedList<String>();
        String line = "";
        try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                while ((line = in.readLine()) != null) {
                        lines.add(line);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return lines;
}

}
