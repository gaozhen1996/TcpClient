package com.smartlab.tsu.view;

import java.io.File;
import java.io.IOException;
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
	
	private static String defIp = "127.0.0.1";

	private static String defPort = "54321";

	private static File configfile = new File(".tcpsystem.data");

	private Map<String, String> configPara;
	
	public static Label connect;
	
	public static Stage stage;
	
	public ConnectionView() {
		if (configfile == null) {
			try {
				configfile.createNewFile();
			} catch (IOException e) {
				System.out.println("创建配置文件失败");
			}
		} else {
			try {
				@SuppressWarnings("unchecked")
				Map<String, String> configPara = (Map<String, String>) FileUtil.readObjectFromFile(configfile);
				String defIptemp = configPara.get("defIp");
				String defPorttemp = configPara.get("defPort");
				if (defIptemp != null && defIptemp.length() > 0 && defPorttemp != null && defPorttemp.length() > 0) {
					defIp = configPara.get("defIp");
					defPort = configPara.get("defPort");
				}

			} catch (Exception e) {
				e.printStackTrace();
				defIp = "127.0.0.1";
				defPort = "54321";
			}

		}
	}

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
		Label ipLabel = new Label("IP:");
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


		ipText.setText(defIp);
		portText.setText(defPort);

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
		
		
		/**
		 * 赋值
		 */
		ConnectionView.connect=connect;
		ConnectionView.stage=stage;
				

		/************************
		 * * 连接 ACTION * *
		 ************************/
		portSwitch.setOnAction((ActionEvent e) -> {
			String host = ipText.getText();
			int port = Integer.parseInt(portText.getText());

			configPara = new HashMap<>();
			configPara.put("defIp", ipText.getText());
			configPara.put("defPort", portText.getText());
			FileUtil.writeObjectToFile(configfile, configPara);

			new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						// 更新主线程
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								connect.setText("连接中...");
							}
						});
						
						TCPFactory.minaClient = new MinaClient();
						TCPFactory.minaClient.config(host, port);
						
					} catch (Exception e2) {
						TCPFactory.minaClient = null;
						// 更新主线程
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
