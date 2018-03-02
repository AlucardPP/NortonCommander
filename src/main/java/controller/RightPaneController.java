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

public class RightPaneController implements Initializable {
	SortFiles sort = new SortFiles();
	ShowFile show = new ShowFile();
	ViewWindowController viewWindowController = new ViewWindowController();

	private final static int mouse_clicker = 2;

	@FXML
	private ComboBox<String> rightSortBox;

	@FXML
	private ListView<File> rightFileShowList;

	@FXML
	private ToggleButton rightChoseSortButton;

	@FXML
	private ComboBox<File> rightDiskChoseBox;

	@FXML
	private Button rightParentButton;

	@FXML
	private TextField rightSearchField;

	public Button getRightParentButton() {
		return rightParentButton;
	}

	public void setRightParentButton(Button rightParentButton) {
		this.rightParentButton = rightParentButton;
	}

	public ListView<File> getRightFileShowList() {
		return rightFileShowList;
	}

	public void setRightFileShowList(ListView<File> rightFileShowList) {
		this.rightFileShowList = rightFileShowList;
	}


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rightSearchField.setPromptText("Search");
		rightChoseSortButton.setGraphic(SortFiles.rightToggleSelected);
		sort.showIconOnToggleButton(rightChoseSortButton, SortFiles.rightToggleSelected);
		ShowFile.showDiskLetter(rightDiskChoseBox);
		show.startingMethod(rightDiskChoseBox, rightFileShowList);
		rightSortBox.setItems(SortFiles.choice);

		rightDiskChoseBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File[] files = rightDiskChoseBox.getValue().listFiles();
				show.showFileList(rightFileShowList, files);
				event.consume();
			}
		});
		rightParentButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ShowFile.moveToParentDirectory(rightFileShowList);
				event.consume();
			}
		});
		rightFileShowList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == mouse_clicker) {
					File file = rightFileShowList.getSelectionModel().getSelectedItem();
					if (file.isDirectory()) {
						File[] files = file.listFiles();
						show.showFileList(rightFileShowList, files);
					} else {
						viewWindowController.showStage(getSelectionPath());
					}
				}
				event.consume();
			}
		});
		rightFileShowList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue == null) {
				return;
			} else {
				String path = oldValue.toString();
				rightSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
					public void handle(KeyEvent event) {
						if (event.getCode().equals(KeyCode.ENTER)) {
							String fileToSearch = rightSearchField.getText();
							rightFileShowList.getItems().clear();
							SearchFiles.search(path, fileToSearch, rightFileShowList);
						}
						if (event.getCode().equals(KeyCode.BACK_SPACE)) {
							rightSearchField.setText("");
							ShowFile.moveToParentDirectory(rightFileShowList);
						}
						event.consume();
					}
				});
			}
		});

		rightSortBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				boolean order;
				String option = rightSortBox.getValue();
				switch (option) {
				case "Type of Element":
					order = rightChoseSortButton.isSelected();
					sort.sortByType(rightFileShowList, order);
					break;
				case "Name":
					order = rightChoseSortButton.isSelected();
					sort.sortByName(rightFileShowList, order);
					break;
				case "Last edit":
					order = rightChoseSortButton.isSelected();
					sort.sortByLastEdit(rightFileShowList, order);
					break;
				case "Size":
					order = rightChoseSortButton.isSelected();
					sort.sortBySize(rightFileShowList, order);
					break;
				default:
					break;
				}
				event.consume();
			}
		});

	}

	public String getSelectionPath() {
		String path = rightFileShowList.getSelectionModel().getSelectedItem().toString();
		if (path == null) {
			return null;
		} else
			return path;
	}

}
