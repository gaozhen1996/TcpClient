package com.smartlab.tsu.controller;

import java.io.File;

import com.smartlab.tsu.util.FileUtil;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IndexViewAction {
   
    
	/**
	 * 清除发送区与接受区的数据
	 * 
	 * @param sendArea
	 * @param recordArea
	 * @param clearData
	 */
	public void ClearDataAction(TextArea sendArea, TextArea recordArea) {
		sendArea.clear();
		recordArea.clear();
	}

	public void openFileAction(Stage primaryStage, TextArea editArea) {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				StringBuffer res = FileUtil.readFileByChars(file);
				editArea.appendText(res.toString());
			}
	}

	public void saveFileAction(Stage primaryStage, TextArea recordArea) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("save file");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				FileUtil.createFile(file, recordArea.getText());
			}
	}

}
