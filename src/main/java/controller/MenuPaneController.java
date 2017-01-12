package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuPaneController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu toolsMenu;

	@FXML
	private MenuItem fileEdit;

	@FXML
	private MenuItem fileExit;

	@FXML
	private MenuItem fileView;

	//TODO unused?

	@FXML
	private Menu helpMenu;

	@FXML
	private Menu ftpMenu;

	@FXML
	private Menu fileMenu;

	@FXML
	private MenuItem ftpConnect;

	public MenuItem getFileEdit() {
		return fileEdit;
	}

	public void setFileEdit(MenuItem fileEdit) {
		this.fileEdit = fileEdit;
	}

	public MenuItem getFileExit() {
		return fileExit;
	}

	public void setFileExit(MenuItem fileExit) {
		this.fileExit = fileExit;
	}

	public MenuItem getFileView() {
		return fileView;
	}

	public void setFileView(MenuItem fileView) {
		this.fileView = fileView;
	}

	public MenuItem getFtpConnect() {
		return ftpConnect;
	}

	public void setFtpConnect(MenuItem ftpConnect) {
		this.ftpConnect = ftpConnect;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
	}

}
