package com.wojo.Vault;

import java.io.IOException;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class fxmlWindowLoaderTest extends ApplicationTest {

	@Test
	public void rootWindowShoudlBeLoad() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/RootWindow.fxml"));
		try {
			AnchorPane root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void mainWindowShoudlBeLoad() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/MainWindow.fxml"));
		try {
			AnchorPane root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loginWindowStep1ShoudlBeLoad() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/LoginWindowStep1.fxml"));
		try {
			AnchorPane root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loginWindowStep2ShoudlBeLoad() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/LoginWindowStep2.fxml"));
		try {
			AnchorPane root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void accountCreatorWindowShoudlBeLoad() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/View/AccountCreator.fxml"));
		try {
			AnchorPane root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
