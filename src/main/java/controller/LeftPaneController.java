package controller;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utils.SearchFiles;
import utils.ShowFile;
import utils.SortFiles;

public class LeftPaneController implements Initializable {
	SortFiles sort = new SortFiles();
	ShowFile show = new ShowFile();
	ViewWindowController viewWindowController = new ViewWindowController();

	private final static int mouse_clicker = 2;

	@FXML
	private Button leftParentButton;

	@FXML
	private ComboBox<File> leftDiskChoseBox;

	@FXML
	private ToggleButton leftChoseSortButton;

	@FXML
	private ListView<File> leftFileShowList;

	@FXML
	private ComboBox<String> leftSortBox;

	@FXML
	private TextField leftSearchField;

	public ListView<File> getLeftFileShowList() {
		return leftFileShowList;
	}

	public void setLeftFileShowList(ListView<File> leftFileShowList) {
		this.leftFileShowList = leftFileShowList;
	}

	public void initialize(URL location, ResourceBundle resources) {
		leftSearchField.setPromptText("Search");
		leftChoseSortButton.setGraphic(SortFiles.leftToggleSelected);
		sort.showIconOnToggleButton(leftChoseSortButton, SortFiles.leftToggleSelected);
		ShowFile.showDiskLetter(leftDiskChoseBox);
		show.startingMethod(leftDiskChoseBox, leftFileShowList);
		leftSortBox.setItems(SortFiles.choice);

		leftDiskChoseBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File[] files = leftDiskChoseBox.getValue().listFiles();
				show.showFileList(leftFileShowList, files);
				event.consume();
			}
		});
		leftParentButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ShowFile.moveToParentDirectory(leftFileShowList);
				event.consume();
			}
		});
		leftFileShowList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == mouse_clicker) {
					File file = leftFileShowList.getSelectionModel().getSelectedItem();
					if (file.isDirectory()) {
						File[] files = file.listFiles();
						show.showFileList(leftFileShowList, files);
					} else {
						viewWindowController.showStage(getSelectionPath());
					}
				}
				event.consume();
			}

		});
		leftFileShowList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue == null) {
				return;
			} else {
				String path = oldValue.toString();
				leftSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
					public void handle(KeyEvent event) {
						if (event.getCode().equals(KeyCode.ENTER)) {
							String fileToSearch = leftSearchField.getText();
							leftFileShowList.getItems().clear();
							SearchFiles.search(path, fileToSearch, leftFileShowList);
						}
						if (event.getCode().equals(KeyCode.BACK_SPACE)) {
							leftSearchField.setText("");
							ShowFile.moveToParentDirectory(leftFileShowList);
						}
						event.consume();
					}
				});

			}
		});

		leftSortBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				boolean order = leftChoseSortButton.isSelected();
				String option = leftSortBox.getValue();
				switch (option) {
				case "Type of Element":
					sort.sortByType(leftFileShowList, order);
					break;
				case "Name":
					sort.sortByName(leftFileShowList, order);
					break;
				case "Last edit":
					sort.sortByLastEdit(leftFileShowList, order);
					break;
				case "Size":
					sort.sortBySize(leftFileShowList, order);
					break;
				default:
					break;
				}
				event.consume();
			}
		});

	}

	public String getSelectionPath() {
		String path = leftFileShowList.getSelectionModel().getSelectedItem().toString();
		if (path == null) {
			return null;
		} else
			return path;
	}

}
