package com.smartlab.tsu.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HelpView {
	
	private static Font fontStyle = Font.font("Microsoft YaHei", FontWeight.BOLD, FontPosture.REGULAR, 20);

	public void init(double width, double height) {
		Label label=new Label("如果在使用中，有任何问题，\n\n"
				+ "请联系smartlab\n\n"
				+ "或访问我们的官网\n\n"
				+ "www.smartlab411.com\n\n"
				+"本软件由smartlab提供技术支持\n\n"
				+"解释权归smartlab所有");
		setStage(width, height, label);
	}

	public void init(double width, double height,String text) {
		Label label=new Label(text);
		setStage(width, height, label);
	}
	
	
	private void setStage(double width, double height,Label text){
		text.setFont(fontStyle);
		text.setAlignment(Pos.CENTER);
		Scene scene=new Scene(text);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Help");
		stage.getIcons().add(new Image("img/home.png"));
		stage.setMinWidth(width / 4);
		stage.setMinHeight(height / 4);
		stage.setScene(scene);
		stage.showAndWait();

	}	
}
