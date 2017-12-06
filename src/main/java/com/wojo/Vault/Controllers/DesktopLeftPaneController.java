package com.wojo.Vault.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DesktopLeftPaneController {

	private RootWindowController rootController;
	
    @FXML
    private JFXButton logOut;
    
    @FXML
    void initialize() {
    	logOut.addEventHandler(ActionEvent.ACTION, e -> {
    		rootController.loadLoginWindowStep1();
    	});
    }

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}
}