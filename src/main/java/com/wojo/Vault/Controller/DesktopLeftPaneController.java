package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DesktopLeftPaneController {

	private RootController rootController;
	
    @FXML
    private JFXButton logOut;
    
    @FXML
    void initialize() {
    	logOut.addEventHandler(ActionEvent.ACTION, e -> {
    		rootController.loadLoginStep1();
    	});
    }

	public void setRootController(RootController rootController) {
		this.rootController = rootController;
	}
}