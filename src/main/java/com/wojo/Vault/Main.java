package com.wojo.Vault;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static final String MAIN_WINDOW_PATH = "/FXML/RootWindow.fxml";

	private static AnchorPane root;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource(MAIN_WINDOW_PATH));
		root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}