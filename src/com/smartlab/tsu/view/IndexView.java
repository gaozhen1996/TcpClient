package com.smartlab.tsu.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartlab.tsu.controller.IndexViewAction;
import com.smartlab.tsu.factory.ConfigFactory;
import com.smartlab.tsu.factory.TCPFactory;
import com.smartlab.tsu.util.ConvertFactory;
import com.smartlab.tsu.util.StringUtil;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class IndexView {

	// 系统处理器架构 例如：AMD64
	private final static String OS_ARCH = System.getProperty("os.arch");

	// 判断是否为嵌入式设备 PS：此处判断的嵌入式设备没有排除手机
	private final static boolean IS_EMBEDDED = OS_ARCH.equals("arm");

	// MainAction
	private static IndexViewAction indexViewAction = new IndexViewAction();

	public static BorderPane root = new BorderPane();

	public Scene scene;
	
	//数据接收区文本
	public static TextArea recordArea;

	private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	public IndexView(Stage primaryStage) {
		init(primaryStage);
	}

	public void init(Stage primaryStage) {

		/**
		 * 屏幕
		 */
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		/**
		 * 最大显示宽度
		 */
		double maxAllowedWidth = primaryScreenBounds.getWidth();

		/**
		 * 最大显示高度
		 */
		double minAllowedHeight = primaryScreenBounds.getHeight();

		/**
		 * TCP连接View
		 */
		ConnectionView connectionView = new ConnectionView();

		/**
		 * 设置窗口大小 判断是否是嵌入式设备 如果 是嵌入式设备----->全屏显示 如果是非嵌入式： 按分辨率分区间显示大小
		 */
		if (IS_EMBEDDED) {
			primaryStage.setX(primaryScreenBounds.getMinX());
			primaryStage.setY(primaryScreenBounds.getMinY());
			primaryStage.setWidth(maxAllowedWidth);
			primaryStage.setHeight(minAllowedHeight);
		} else {
			if (maxAllowedWidth <= 1024) {
				primaryStage.setWidth(640);
				primaryStage.setHeight(480);
			} else if (maxAllowedWidth <= 1366) {
				primaryStage.setWidth(1024);
				primaryStage.setHeight(600);
			} else {
				primaryStage.setWidth(1366);
				primaryStage.setHeight(768);
			}
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setResizable(false);
		}

		/*****************************************
		 ************ header box BEGIN *********
		 ****************************************/
		VBox header = new VBox();
		header.setId("header");
		header.setPrefHeight(minAllowedHeight / 25);

		/**
		 * menu button
		 */
		HBox menuBtnBox = new HBox();
		menuBtnBox.setId("menuBtnBox");
		menuBtnBox.setPrefHeight(minAllowedHeight / 25);

		/**
		 * open Box
		 */
		VBox openBox = new VBox();
		openBox.setId("menuButton");
		openBox.setAlignment(Pos.CENTER);
		Label openLab = new Label("    open    ");
		openBox.getChildren().setAll(new ImageView(new Image(getClass().getResource("/img/open.png").toExternalForm())),
				openLab);

		VBox saveBox = new VBox();
		saveBox.setId("menuButton");
		saveBox.setAlignment(Pos.CENTER);
		Label saveLab = new Label("    save    ");
		saveBox.getChildren().setAll(new ImageView(new Image(getClass().getResource("/img/save.png").toExternalForm())),
				saveLab);

		/**
		 * connect Box
		 */
		VBox cntBox = new VBox();
		cntBox.setId("menuButton");
		cntBox.setAlignment(Pos.CENTER);
		Label cntLab = new Label("   Connect  ");
		cntBox.getChildren()
				.setAll(new ImageView(new Image(getClass().getResource("/img/connect.png").toExternalForm())), cntLab);

		/**
		 * disconnect Box
		 */
		VBox discntBox = new VBox();
		discntBox.setAlignment(Pos.CENTER);
		Label discntLab = new Label(" DisConnect ");
		discntBox.getChildren().setAll(
				new ImageView(new Image(getClass().getResource("/img/discon.png").toExternalForm())), discntLab);

		/**
		 * clear Box
		 */
		VBox clearBox = new VBox();
		clearBox.setId("menuButton");
		clearBox.setAlignment(Pos.CENTER);
		Label clearLab = new Label("  ClearData ");
		clearBox.getChildren()
				.setAll(new ImageView(new Image(getClass().getResource("/img/clear.png").toExternalForm())), clearLab);

		/**
		 * common box
		 */
		VBox commonBox = new VBox();
		commonBox.setId("menuButton");
		commonBox.setAlignment(Pos.CENTER);
		Label commonLab = new Label("  Common");
		commonBox.getChildren().setAll(
				new ImageView(new Image(getClass().getResource("/img/common.png").toExternalForm())), commonLab);

		/**
		 * parse box
		 */
		VBox parseBox = new VBox();
		parseBox.setId("menuButton");
		parseBox.setAlignment(Pos.CENTER);
		Label parseLab = new Label("  Analysis");
		parseBox.getChildren()
				.setAll(new ImageView(new Image(getClass().getResource("/img/parse.png").toExternalForm())), parseLab);

		/**
		 * Help box
		 */
		VBox helpBox = new VBox();
		helpBox.setId("menuButton");
		helpBox.setAlignment(Pos.CENTER);
		Label helpLab = new Label("    Help    ");
		helpBox.getChildren().setAll(new ImageView(new Image(getClass().getResource("/img/help.png").toExternalForm())),
				helpLab);

		menuBtnBox.getChildren().setAll(openBox, saveBox, cntBox, discntBox, clearBox, parseBox, helpBox);
		header.getChildren().addAll(menuBtnBox);

		/*******************************************
		 ************** header box END **************
		 *******************************************/

		/*******************************************
		 ************** Body BEGIN **************
		 *******************************************/
		BorderPane body = new BorderPane();

		/**
		 * 数据接收区
		 */
		VBox recordBox = new VBox();
		recordBox.setAlignment(Pos.CENTER);
		recordBox.setPadding(new Insets(5, 10, 20, 10));
		recordBox.setId("recordPane");
		Text recordTitle = new Text("数据接收区");
		recordTitle.setId("title");
		recordArea = new TextArea();
		recordArea.setWrapText(true);
		recordArea.setPrefWidth(primaryStage.getWidth() * 0.49);
		recordArea.setPrefHeight(primaryStage.getHeight() * 0.7);
		recordArea.setEditable(false);
		recordBox.getChildren().addAll(recordTitle, recordArea);

		/**
		 * 数据发送区
		 */
		VBox sendBox = new VBox();
		sendBox.setAlignment(Pos.CENTER);
		sendBox.setPadding(new Insets(5, 10, 20, 10));
		sendBox.setId("sendPane");
		Text sendTitle = new Text("数据发送区");
		sendTitle.setId("title");
		TextArea sendArea = new TextArea();
		sendArea.setWrapText(true);
		sendArea.setPrefWidth(primaryStage.getWidth() * 0.475);
		sendArea.setPrefHeight(primaryStage.getHeight() * 0.7);
		sendArea.setEditable(false);
		sendBox.getChildren().addAll(sendTitle, sendArea);

		/**
		 * 编辑区
		 */
		HBox orderBox = new HBox();
		orderBox.setId("orderBox");
		orderBox.setPadding(new Insets(10, 10, 10, 10));
		orderBox.setSpacing(15);
		orderBox.setAlignment(Pos.CENTER);
		TextArea editArea = new TextArea();
		editArea.setTooltip(new Tooltip("例如：03 FF FF FF FF FF FF FF FF"));
		editArea.setPromptText("请输入调试指令,按Enter发送");
		editArea.getProperties().put("vkType", "text");
		editArea.setPrefWidth(primaryStage.getWidth() * 0.8);
		editArea.setPrefHeight(primaryStage.getHeight() * 0.2);
		Button send = new Button("发送");
		send.setPrefHeight(primaryStage.getHeight() * 0.2);
		orderBox.getChildren().addAll(editArea, send);

		body.setLeft(sendBox);
		body.setRight(recordBox);
		body.setBottom(orderBox);

		/*******************************************
		 ************** Body END **************
		 *******************************************/

		/*******************************************
		 ************** footer BEGIN **************
		 *******************************************/
		VBox footer = new VBox();

		footer.setId("footer");
		footer.setPrefHeight(primaryStage.getHeight() / 25);
		Label connectLab = new Label("未连接");
		footer.getChildren().add(connectLab);
		/*******************************************
		 ************** footer END **************
		 *******************************************/

		root.setTop(header);
		root.setBottom(footer);

		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Index.css").toExternalForm());

		/*******************************************
		 ************** Action **************
		 *******************************************/

		// 打开文件,并将读取的字符输入编辑框
		openBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				indexViewAction.openFileAction(primaryStage, editArea);
			}
		});

		// 保存文件
		saveBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				indexViewAction.saveFileAction(primaryStage, recordArea);
			}
		});

		// 连接TCP
		cntBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				if (TCPFactory.minaClient == null) {
					connectionView.options(primaryStage.getWidth(),
							primaryStage.getHeight(), connectLab);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (TCPFactory.minaClient != null) {
						discntBox.setId("menuButton");
						cntBox.setId("");
						root.setCenter(body);
					}
				}
			}
		});

		// 断开连接
		discntBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				TCPFactory.minaClient.getConnector().dispose();
				TCPFactory.minaClient=null;
				connectLab.setText("未连接");
				root.setCenter(null);
			}
		});

		
		/**
		 *  发送普通字符串消息
		 */
		send.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				String message = editArea.getText();
				if(ConfigFactory.parseStyle.equals("String")) {
					TCPFactory.minaClient.sendMessage(message.getBytes());
					sendArea.appendText(sdf.format(new Date()) +" 发送:\t"+message+"\n");
				}else {
					message = StringUtil.replaceBlank(message);
					byte[] megByte=ConvertFactory.hexStringToBytes(message);
					TCPFactory.minaClient.sendMessage(megByte);
					String result=ConvertFactory.bytesToHexString(megByte);
					sendArea.appendText(sdf.format(new Date()) +" 发送:\t"+result+"\n");
				}
			}
		});
		
		
		// 清除数据
		clearBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				indexViewAction.ClearDataAction(sendArea, recordArea);
			}
		});

		/**
		 * String解析
		 */
		commonBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				menuBtnBox.getChildren().set(5, parseBox);
				ConfigFactory.parseStyle="hex";
			}
		});

		/**
		 * 十六进制解析Box，点击后变为普通字符串
		 */
		parseBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				menuBtnBox.getChildren().set(5, commonBox);
				ConfigFactory.parseStyle="String";
			}
		});

		// Help
		helpBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				new HelpView().init(primaryStage.getWidth(), primaryStage.getWidth());
			}
		});

		// 监听窗体关闭事件
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});

		/**
		 * 发送数据
		 */
		editArea.setOnKeyPressed(new EventHandler<KeyEvent>() {  
	        @Override  
	        public void handle(KeyEvent event) {  
	            if(event.getCode() == KeyCode.ENTER){  
					String message = editArea.getText();
					if(ConfigFactory.parseStyle.equals("String")) {
						TCPFactory.minaClient.sendMessage(message.getBytes());
						sendArea.appendText(sdf.format(new Date()) +" 发送:\t"+message+"\n");
					}else {
						message = StringUtil.replaceBlank(message);
						byte[] megByte=ConvertFactory.hexStringToBytes(message);
						TCPFactory.minaClient.sendMessage(megByte);
						String result=ConvertFactory.bytesToHexString(megByte);
						sendArea.appendText(sdf.format(new Date()) +" 发送:\t"+result+"\n");
					}
	            }  
	        }  
	    });  
		
	}
}
