package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alucard on 2016-12-09.
 */
public class RightPaneController implements Initializable {
    ShowFiles showFiles = new ShowFiles();
    SortFiles sortFiles = new SortFiles();
    SearchFiles searchFiles = new SearchFiles();
    private final static int mouse_clicker = 2;
    @FXML
    private TextField rightSearchArea;

    @FXML
    private ComboBox<File> rightComboBox;

    @FXML
    private ComboBox<String> rightSortBox;

    @FXML
    private ToggleButton rightSortOption;

    @FXML
    private ListView<File> rightListView;

    @FXML
    private Button rightUpButton;

    public ListView<File> getRightListView() {
        return rightListView;
    }

    public Button getRightUpButton() {
        return rightUpButton;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showFiles.addRoot(rightComboBox);
        rightSearchArea.setPromptText("Search");
        rightSortOption.setGraphic(sortFiles.toggleSelected);
        sortFiles.showIconOnToggleButton(rightSortOption);
        rightSortBox.setItems(sortFiles.choice);
        rightComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File[] files = rightComboBox.getValue().listFiles();
                showFiles.showListFile(files, rightListView);
            }
        });
        rightListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == mouse_clicker) {
                    File file = rightListView.getSelectionModel().getSelectedItem();
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        showFiles.showListFile(files, rightListView);
                    }
                }
            }
        });
        rightListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == null) {
                return;
            } else {
                String path = oldValue.toString();
                searchFiles.searchFile(path, rightSearchArea, rightListView);
                showFiles.moveToParentDirectory(path, rightListView, rightUpButton);
            }
        }));
        rightSortBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean order = rightSortOption.isSelected();
                if (newValue == "Type of Element") {
                    sortFiles.sortByType(rightListView,order);
                } else if (newValue == "Name") {
                    sortFiles.sortByName(rightListView,order);
                } else if (newValue == "Last edit") {
                    sortFiles.sortByLastEdit(rightListView,order);
                } else if (newValue == "Size") {
                    sortFiles.sortBySize(rightListView,order);
                } else return;


            }
        });

    }
}
