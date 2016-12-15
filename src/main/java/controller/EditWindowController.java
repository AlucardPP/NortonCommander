package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.*;
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
public class EditWindowController implements Initializable {
    private final String[] extension = new String[]{"txt", "xml", "html", "css", "ini"};
    private final static String resource = "/view/EditWindow.fxml";


    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private Button saveButton;

    @FXML
    private TextArea showArea;

    public TextArea getShowArea() {
        return showArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });


        showArea.setWrapText(true);


    }

    public void createStage(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = (Parent) loader.load();
            EditWindowController editWindowController = loader.getController();
            editWindowController.showEditableFile(path, editWindowController.getShowArea(),editWindowController.getSaveButton());
            editWindowController.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    saveFile(path, editWindowController.getShowArea());
                }
            });
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit File");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditableFile(String path, TextArea showArea, Button button) {
        File file = new File(path);
        if (file.isDirectory()) {
            showArea.setText("I cannot show you directory as a text. You know it well, are you? :D");
            button.setDisable(true);
        } else if (checkExtension(file) == false) {
            showArea.setText("I can't show you exactly this file :) " +
                    "(I'm not cooperate with this extension, who do you think I am? Slutty viewer? " +
                    "I have my own personal guest in \"txt\", \"xml\", \"html\", \"css\", \"ini\" ;P )");
            button.setDisable(true);
        } else if (checkExtension(file) == true) {
            try (FileReader fr = new FileReader(file);
                 BufferedReader br = new BufferedReader(fr);) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                showArea.setText(sb.toString());
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkExtension(File file) {
        for (String check : extension) {
            if (file.getName().toLowerCase().endsWith(check)) {
                return true;
            }
        }
        return false;
    }

    public void saveFile(String path, TextArea textArea) {
        File file = new File(path);
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write(textArea.getText());

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
