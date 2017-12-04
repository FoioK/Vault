package com.wojo.Vault.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class RootWindowController {

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {
    	loadLoginWindowStep1();
    }
    
	private void loadMainWindow() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/FXML/MainWindow.fxml"));
		AnchorPane mainPane = null;
		try {
			mainPane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		root.getChildren().add(mainPane);
	}
	
	public void loadLoginWindowStep1() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/FXML/LoginWindowStep1.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pane.setLayoutX(225);
		pane.setLayoutY(100);
		
		LoginWindowStep1Controller controller = loader.getController();
		controller.setRootController(this);
		setScreen(pane);
	}
	
	public void setScreen(Node pane) {
		root.getChildren().clear();
		loadMainWindow();
		root.getChildren().add(pane);
	}
}