package com.smartlab.tsu;

import com.smartlab.tsu.view.IndexView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
* <p>
* Description:主类
* <p>
* Company: SMARTLAB 
* @author gaozhen
* @date 2018年6月30日
* @Version 1.1
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		IndexView indexView=new IndexView(primaryStage);
		Scene scene = indexView.scene;

		primaryStage.getIcons().add(new Image("img/home.png"));
		primaryStage.setTitle("SMARTLAB TCP调试工具");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}