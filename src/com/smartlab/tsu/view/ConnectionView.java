package com.smartlab.tsu.view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.smartlab.tsu.factory.TCPFactory;
import com.smartlab.tsu.mina.MinaClient;
import com.smartlab.tsu.util.FileUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnectionView {

//	private static String defIp = "192.168.1.107";
	private static String defIp = "127.0.0.1";
//	private static String defIp = "47.93.222.143";
	
	public Map<String, Object> options(double width, double height, Label connect) {

		Map<String, Object> parameter = new HashMap<String, Object>();

		/*********************
		 * BODY *
		 *********************/
		GridPane managePane = new GridPane();

		/**
		 * 设置控件之间边距
		 */
		managePane.setVgap(25);
		managePane.setHgap(10);
		managePane.setPadding(new Insets(10, 10, 10, 10));
		/**
		 * 管理面板标题
		 */
		Text manageTitle = new Text("SMARTLAB");
		manageTitle.setId("title");
		managePane.add(manageTitle, 2, 0, 4, 2);

		/**
		 * ip设置控件
		 */
		Label ipLabel = new Label("TP:");
		TextField ipText = new TextField();
		managePane.add(ipLabel, 0, 3);
		managePane.add(ipText, 2, 3);

		/**
		 * 端口设置控件
		 */
		Label portLabel = new Label("端口:");
		TextField portText = new TextField();
		managePane.add(portLabel, 0, 5);
		managePane.add(portText, 2, 5);
		
		
		File file=new File("IP.txt");
		//设置默认值
		if(file.exists()) {
			String IP=FileUtil.readFileByChars(file).toString();
			ipText.setText(IP);
		}else {
			ipText.setText(defIp);
		}

		portText.setText("54321");
		
		/**
		 * 打开串口按钮
		 */
		Button portSwitch = new Button("连接");
		managePane.add(portSwitch, 0, 7, 3, 1);
		GridPane.setHalignment(portSwitch, HPos.CENTER);

		
		VBox layout = new VBox();
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("connection.css").toExternalForm());
		layout.getChildren().addAll(managePane);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("connect port");
		stage.getIcons().add(new Image("img/home.png"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinWidth(width / 4);
		stage.setMinHeight(height / 2);
		stage.setScene(scene);

		/************************
		 * * ACTION * *
		 ************************/
		portSwitch.setOnAction((ActionEvent e) -> {
			String host = ipText.getText();
			int port = Integer.parseInt(portText.getText());

			new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						
						//更新主线程
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	connect.setText(host + " : " + port + "已连接");
						    	stage.close();
						    }
						});
						
						TCPFactory.minaClient = new MinaClient();
						TCPFactory.minaClient.config(host, port);
						
					} catch (Exception e2) {
						TCPFactory.minaClient=null;
						//更新主线程
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	connect.setText("未连接");
						    	Alert error = new Alert(Alert.AlertType.INFORMATION, "服务器拒绝连接!");
								error.showAndWait();
						    }
						});
					}

				}
			}) {
			}.start();
			
			
		});

		// 使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
		stage.showAndWait();
		return parameter;
	}

}
