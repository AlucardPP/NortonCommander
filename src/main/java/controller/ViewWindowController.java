package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ViewWindowController implements Initializable {

	EditWindowController editWindowController=new EditWindowController();
    private final static String resource="/view/ViewWindow.fxml";
    private final String[] extension = new String[]{"txt", "xml", "html", "css", "ini"};

	@FXML
	private Button cancelButton;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TextArea viewText;

//TODO to się nadaje tylko na kfiotki w kodzie!

	public TextArea getViewText() {
		return viewText;
	}
	public void setViewText(TextArea viewText) {
		this.viewText = viewText;
	}
	@Override
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
	            viewWindowController.showEditableFile(path, viewWindowController.getViewText());
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setTitle("View File");
	            stage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void showEditableFile(String path, TextArea viewText) {
	        File file = new File(path);
	        if (file.isDirectory()) {
	            viewText.setText("I cannot show you directory as a text. You know it well, are you? :D");

	        } else if  (checkExtension(file) == false) {
	            viewText.setText("I can't show you exactly this file :) " +
	                    "(I'm not cooperate with this extension, who do you think I am? Slutty viewer? " +
	                    "I have my own personal guest in \"txt\", \"xml\", \"html\", \"css\", \"ini\" ;P )");

	        } else if (checkExtension(file) == true) {
	            try (FileReader fr = new FileReader(file);
	                 BufferedReader br = new BufferedReader(fr);) {
	                StringBuilder sb = new StringBuilder();
	                String line = br.readLine();
	                while (line != null) {
	                    sb.append(line);
	                    line = br.readLine();
	                }
	                viewText.setText(sb.toString());
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

}
