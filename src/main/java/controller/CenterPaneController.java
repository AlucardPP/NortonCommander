package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import utils.DownloadFile;
import utils.ShowFile;

public class CenterPaneController implements Initializable {
	ShowFile show = new ShowFile();
	DownloadFile downloadFile = new DownloadFile();

	@FXML
	private Button downloadButton;

	@FXML
	private Button copyButton;

	@FXML
	private Button moveButton;

	@FXML
	private Button uploadButton;

	public Button getDownloadButton() {
		return downloadButton;
	}

	public void setDownloadButton(Button downloadButton) {
		this.downloadButton = downloadButton;
	}

	public Button getCopyButton() {
		return copyButton;
	}

	public void setCopyButton(Button copyButton) {
		this.copyButton = copyButton;
	}

	public Button getMoveButton() {
		return moveButton;
	}

	public void setMoveButton(Button moveButton) {
		this.moveButton = moveButton;
	}

	public Button getUploadButton() {
		return uploadButton;
	}


	
	public void setUploadButton(Button uploadButton) {
		this.uploadButton = uploadButton;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}

	public void copyFilesAndDirectories(ListView<File> leftListView, ListView<File> rightListView, Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int leftIndex = leftListView.getSelectionModel().getSelectedIndex();
				int rightIndex = rightListView.getSelectionModel().getSelectedIndex();
				ObservableList<File> rightList = rightListView.getItems();
				ObservableList<File> leftList = leftListView.getItems();
				if (leftListView.getSelectionModel().isSelected(leftIndex)) {

					
					File fileToCopy = leftListView.getSelectionModel().getSelectedItem();
					String name = fileToCopy.getName();
					File directory = new File(rightList.get(rightList.size() - 1).getParent().toString());
					File destination = new File(directory + File.separator + name);
					try {
						copyFile(fileToCopy, destination);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (rightListView.getSelectionModel().isSelected(rightIndex)) {
					File fileToCopy = rightListView.getSelectionModel().getSelectedItem();
					String name = fileToCopy.getName();
					File directory = new File(leftList.get(leftList.size() - 1).getParent().toString());
					File destination = new File(directory + File.separator + name);
					try {
						copyFile(fileToCopy, destination);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (leftListView.getSelectionModel().isSelected(leftIndex)
						&& rightListView.getSelectionModel().isSelected(rightIndex)) {
					return;
				} else {
					return;
				}
				refreshList(leftListView, rightListView);

				event.consume();
			}
		});
	}

	public void copyFile(File source, File destination) throws IOException {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdir();
			}
			String[] files = source.list();
			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);
				copyFile(srcFile, destFile);
			}
		} else {
			Files.copy(source.toPath(), destination.toPath());
		}
	}

	public void moveFiles(ListView<File> leftList, ListView<File> rightList, Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int leftIndex = leftList.getSelectionModel().getSelectedIndex();
				int rightIndex = rightList.getSelectionModel().getSelectedIndex();
				ObservableList<File> rightL = rightList.getItems();
				ObservableList<File> leftL = leftList.getItems();
				if (leftList.getSelectionModel().isSelected(leftIndex)) {
					File fileToCopy = leftList.getSelectionModel().getSelectedItem();
					String name = fileToCopy.getName();
					File directory = new File(rightL.get(rightL.size() - 1).getParent().toString());
					File destination = new File(directory + File.separator + name);
					try {
						copyFile(fileToCopy, destination);
						File fileToDelete = leftList.getSelectionModel().getSelectedItem();
						fileToDelete.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (rightList.getSelectionModel().isSelected(rightIndex)) {
					File fileToCopy = rightList.getSelectionModel().getSelectedItem();
					String name = fileToCopy.getName();
					File directory = new File(leftL.get(leftL.size() - 1).getParent().toString());
					File destination = new File(directory + File.separator + name);
					try {
						copyFile(fileToCopy, destination);
						File fileToDelete = rightList.getSelectionModel().getSelectedItem();
						fileToDelete.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (leftList.getSelectionModel().isSelected(leftIndex)
						&& rightList.getSelectionModel().isSelected(rightIndex)) {
					return;
				} else {
					return;
				}

				refreshList(leftList, rightList);

				event.consume();
			}
		});

	}

	public void refreshList(ListView<File> leftList, ListView<File> rightList) {
		ObservableList<File> right = rightList.getItems();
		ObservableList<File> left = leftList.getItems();
		//TODO dodać linię
		String leftFile = leftList.getItems().get(left.size() - 1).getParent();
		String rightFile = rightList.getItems().get(right.size() - 1).getParent();
		//TODO dodać linię
		File[] leftFiles = new File(leftFile).listFiles();
		File[] rightFiles = new File(rightFile).listFiles();
		//TODO dodać linię
		show.showFileList(leftList, leftFiles);
		show.showFileList(rightList, rightFiles);

	}

	public void downloadButtonAction(ListView<File> leftList, ListView<File> rightList, FTPClient ftpClient) {
		leftList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {

			getDownloadButton().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String saveDir = oldValue.toString();
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								downloadFile.download(rightList, saveDir, ftpClient);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
		}));

	}

	public void uploadButtonAction(ListView<File> leftList, FTPClient ftpClient) {
		getUploadButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String localFilePath = leftList.getSelectionModel().getSelectedItem().toString();
					File file = new File(localFilePath);
					//TODO - nie wykorzystywane
					String localParentDir = file.getParent();
					String remoteDirPath = ftpClient.printWorkingDirectory();
					//TODO znów klasa anonimowa ?
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								downloadFile.upload(ftpClient, leftList);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
