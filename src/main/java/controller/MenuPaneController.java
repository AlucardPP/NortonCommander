package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by Alucard on 2016-12-09.
 */
public class MenuPaneController implements Initializable{
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

    public MenuItem getFileView() {
        return fileView;
    }

    public MenuItem getFtpConnect() {
        return ftpConnect;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fileExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

    }
}
