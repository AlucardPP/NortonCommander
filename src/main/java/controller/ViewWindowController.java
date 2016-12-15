package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Alucard on 2016-12-09.
 */
public class ViewWindowController implements Initializable {
    EditWindowController editWindowController=new EditWindowController();
    private final static String resource="/view/ViewWindow.fxml";

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea viewText;

    public TextArea getViewText() {
        return viewText;
    }

    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });
        viewText.setWrapText(true);

    }
    public void showStage(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = (Parent) loader.load();
            ViewWindowController viewWindowController = loader.getController();
            editWindowController.showEditableFile(path, viewWindowController.getViewText(),null);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("View File");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
