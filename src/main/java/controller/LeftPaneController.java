package controller;



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
public class LeftPaneController implements Initializable {
    ShowFiles showFiles = new ShowFiles();
    SearchFiles searchFiles = new SearchFiles();
    SortFiles sortFiles = new SortFiles();
    private final static int mouse_clicker = 2;

    @FXML
    private Button leftUpButton;

    @FXML
    private ToggleButton leftSortOption;

    @FXML
    private ComboBox<File> leftComboBox;

    @FXML
    private ComboBox<String> leftSortBox;

    @FXML
    private ListView<File> leftListView;

    @FXML
    private TextField leftSearchField;

    public ListView<File> getLeftListView() {
        return leftListView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showFiles.addRoot(leftComboBox);
        leftSortOption.setGraphic(sortFiles.toggleSelected);
        sortFiles.showIconOnToggleButton(leftSortOption);
        leftSearchField.setPromptText("Search");
        leftSortBox.setItems(sortFiles.choice);
        leftComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File[] files = leftComboBox.getValue().listFiles();
                showFiles.showListFile(files, leftListView);
            }
        });
        leftListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == mouse_clicker) {
                    File file = leftListView.getSelectionModel().getSelectedItem();
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        showFiles.showListFile(files, leftListView);
                    } else {
                        return;
                    }
                }
            }
        });
        leftListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == null) {
                return;
            } else {
                String path = oldValue.toString();
                showFiles.moveToParentDirectory(path, leftListView, leftUpButton);
                searchFiles.searchFile(path, leftSearchField, leftListView);
            }
        }));

        leftSortBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean order=leftSortOption.isSelected();
                String option=leftSortBox.getValue();
                switch (option){
                    case "Type of Element":
                        sortFiles.sortByType(leftListView,order);
                        break;
                    case "Name":
                        sortFiles.sortByName(leftListView,order);
                        break;
                    case "Last edit":
                        sortFiles.sortByLastEdit(leftListView,order);
                        break;
                    case "Size":
                        sortFiles.sortBySize(leftListView,order);
                        break;
                    default:
                        break;
                }
            }
        });


    }


}

