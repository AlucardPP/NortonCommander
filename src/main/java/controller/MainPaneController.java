package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainPaneController implements Initializable {

	EditWindowController editWindowController = new EditWindowController();
	ViewWindowController viewWindowController = new ViewWindowController();
	FTPWindowController ftpWindowController = new FTPWindowController();

	@FXML
	private MenuPaneController menuPaneController;

	@FXML
	private CenterPaneController centerPaneController;

	@FXML
	private LeftPaneController leftPaneController;

	@FXML
	private RightPaneController rightPaneController;

	//TODO w konkursie na najbardziej zjebaną metodę to byś chyba wygrał. Jak widzę takie takie coś to w uszach brzmi "welkom maj frient"
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ListView<File> leftListView = leftPaneController.getLeftFileShowList();
		ListView<File> rightListView = rightPaneController.getRightFileShowList();
		Button copyButton = centerPaneController.getCopyButton();
		Button moveButton = centerPaneController.getMoveButton();
		Button downloadButton = centerPaneController.getDownloadButton();
		Button uploadButton = centerPaneController.getUploadButton();
		Button rigtUpButton = rightPaneController.getRightParentButton();
		MenuItem editFile = menuPaneController.getFileEdit();
		MenuItem viewFile = menuPaneController.getFileView();
		MenuItem ftpClient = menuPaneController.getFtpConnect();
		centerPaneController.copyFilesAndDirectories(leftListView, rightListView, copyButton);
		centerPaneController.moveFiles(leftListView, rightListView, moveButton);

		editFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int leftIndex = leftListView.getSelectionModel().getSelectedIndex();
				int rightIndex = rightListView.getSelectionModel().getSelectedIndex();
				if (leftListView.getSelectionModel().isSelected(leftIndex)) {
					String path = leftListView.getSelectionModel().getSelectedItem().toString();
					editWindowController.createStage(path);
				} else if (rightListView.getSelectionModel().isSelected(rightIndex)) {
					String path = rightListView.getSelectionModel().getSelectedItem().toString();
					editWindowController.createStage(path);
				} else if (leftListView.getSelectionModel().isSelected(leftIndex)
						&& rightListView.getSelectionModel().isSelected(rightIndex)) {
					return;
				} else
					return;

			}

		});

		viewFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int leftIndex = leftListView.getSelectionModel().getSelectedIndex();
				int rightIndex = rightListView.getSelectionModel().getSelectedIndex();
				if (leftListView.getSelectionModel().isSelected(leftIndex)) {
					String path = leftListView.getSelectionModel().getSelectedItem().toString();
					viewWindowController.showStage(path);
				} else if (rightListView.getSelectionModel().isSelected(rightIndex)) {
					String path = rightListView.getSelectionModel().getSelectedItem().toString();
					viewWindowController.showStage(path);
				} else if (leftListView.getSelectionModel().isSelected(leftIndex)
						&& rightListView.getSelectionModel().isSelected(rightIndex)) {
					return;
				} else
					return;
			}
		});
		ftpClient.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ftpWindowController.setStage(rightListView, rigtUpButton, leftListView);
			}
		});

	}

}
