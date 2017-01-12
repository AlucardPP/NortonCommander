package controller;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class FTPWindowController implements Initializable {

	private final static String resource = "/view/FtpWindow.fxml";
	CenterPaneController centerPaneController = new CenterPaneController();

	@FXML
	private TextField usernameField;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField hostField;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField portField;

	@FXML
	private Button connectButton;

	public Button getConnectButton() {
		return connectButton;
	}

	public void setConnectButton(Button connectButton) {
		this.connectButton = connectButton;
	}

	public TextField getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(TextField usernameField) {
		this.usernameField = usernameField;
	}

	public TextField getHostField() {
		return hostField;
	}

	public void setHostField(TextField hostField) {
		this.hostField = hostField;
	}

	public TextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(TextField passwordField) {
		this.passwordField = passwordField;
	}

	public TextField getPortField() {
		return portField;
	}

	public void setPortField(TextField portField) {
		this.portField = portField;
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
		usernameField.setPromptText("Username");
		passwordField.setPromptText("Password");
		hostField.setPromptText("Host");
		portField.setPromptText("Port");

	}

	public void setStage(ListView<File> rightList, Button rightUpButton, ListView<File> leftListView) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			//TODO zbędne castowanie
			Parent root = (Parent) loader.load();
			FTPWindowController ftpWindowController = loader.getController();
			//TODO to się robi już niezdrowe, znów qrwa klasa anonimowa ?
			ftpWindowController.getConnectButton().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// String ftpUrl = "ftp://%s:%s@%s/%s;type=d";
					FTPClient ftpClient = new FTPClient();
					String host = ftpWindowController.getHostField().getText();
					String user = ftpWindowController.getUsernameField().getText();
					String password = ftpWindowController.getPasswordField().getText();
					int port = Integer.parseInt(ftpWindowController.getPortField().getText());
					connectTo(host, user, password, port, rightList, ftpClient);
					changeDirectoryInFTP(rightList, ftpClient);
					moveToParentDirectory(rightUpButton, ftpClient, rightList);
					centerPaneController.downloadButtonAction(leftListView, rightList, ftpClient);
					centerPaneController.uploadButtonAction(leftListView, ftpClient);
					Stage stage = (Stage) ftpWindowController.getConnectButton().getScene().getWindow();
					stage.close();
				}

			});

			Stage secondStage = new Stage();
			secondStage.setScene(new Scene(root));
			secondStage.setTitle("Ftp Connect");
			secondStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveToParentDirectory(Button button, FTPClient ftpClient, ListView<File> listView) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					ftpClient.changeToParentDirectory();
					FTPFile[] ftpFiles = ftpClient.listFiles();
					showServerFiles(ftpFiles, listView);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void changeDirectoryInFTP(ListView<File> list, FTPClient client) {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					try {
						String path = client.printWorkingDirectory() + "/";
						String folder = list.getSelectionModel().getSelectedItem().toString();
						String full = path + folder;
						client.changeWorkingDirectory(full);
						FTPFile[] fi = client.listFiles();
						showServerFiles(fi, list);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	public void showServerFiles(FTPFile[] files, ListView listView) {
		listView.getItems().clear();
		for (FTPFile f : files) {
			listView.getItems().add(f.getName());
		}
	}

	public void connectTo(String host, String user, String password, int port, ListView list, FTPClient ftp) {

		try {
			ftp.connect(host, port);
			showServerReply(ftp);
			ftp.login(user, password);
			FTPFile[] files = ftp.listFiles();

			list.getItems().clear();
			for (FTPFile file : files) {

				list.getItems().add(file.getName());
			}
			// ftpClient.logout();
			// ftpClient.disconnect();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
	}

}
