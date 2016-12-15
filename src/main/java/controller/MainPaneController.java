package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;



import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alucard on 2016-12-09.
 */
public class MainPaneController implements Initializable {
    EditWindowController editWindowController=new EditWindowController();
    ViewWindowController viewWindowController=new ViewWindowController();
    FTPWindowController ftpWindowController=new FTPWindowController();

    @FXML
    private MenuPaneController menuPaneController;

    @FXML
    private CenterPaneController centerPaneController;

    @FXML
    private LeftPaneController leftPaneController;

    @FXML
    private RightPaneController rightPaneController;


    public void initialize(URL location, ResourceBundle resources) {
        Button copyButton=centerPaneController.getCopyButton();
        Button moveButton=centerPaneController.getMoveButton();
        Button rigtUpButton=rightPaneController.getRightUpButton();
        ListView<File> leftListView=leftPaneController.getLeftListView();
        ListView<File> rightListView=rightPaneController.getRightListView();
        MenuItem editFile=menuPaneController.getFileEdit();
        MenuItem viewFile=menuPaneController.getFileView();
        MenuItem ftpClient=menuPaneController.getFtpConnect();
        centerPaneController.copyFilesAndDirectories(leftListView,rightListView,copyButton);
        centerPaneController.copyFilesAndDirectories(rightListView,leftListView,copyButton);
        centerPaneController.moveFiles(leftListView,rightListView,moveButton);
        centerPaneController.moveFiles(rightListView,leftListView,moveButton);

        editFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editWindowController.createStage(getSelectionPath());
            }

        });
        viewFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewWindowController.showStage(getSelectionPath());
            }
        });
        ftpClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ftpWindowController.setStage(rightListView,rigtUpButton,leftListView);
            }
        });

    }
    public String getSelectionPath() {
        String path = leftPaneController.getLeftListView().getSelectionModel().getSelectedItem().toString();
        if (path==null){
            return null;
        }
        else return path;
    }
}
