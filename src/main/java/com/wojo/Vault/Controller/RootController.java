package com.wojo.Vault.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class RootController {

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {
    	loadLoginStep1();
    }
    
	private void loadMainWindow() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/Main.fxml"));
		AnchorPane mainPane = null;
		try {
			mainPane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		root.getChildren().add(mainPane);
	}
	
	public void loadLoginStep1() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/LoginStep1.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pane.setLayoutX(225);
		pane.setLayoutY(100);
		
		LoginStep1Controller controller = loader.getController();
		controller.setRootController(this);
		setScreen(pane);
	}
	
	public void setScreen(Node pane) {
		root.getChildren().clear();
		loadMainWindow();
		root.getChildren().add(pane);
	}
}