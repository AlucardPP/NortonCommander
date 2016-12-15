package controller;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * Created by Alucard on 2016-12-09.
 */
public class CenterPaneController implements Initializable {
    DownloadFile downloadFile=new DownloadFile();

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
                File fileToCopy = leftListView.getSelectionModel().getSelectedItem();
                String name = fileToCopy.getName();
                ObservableList<File> list = rightListView.getItems();
                File directory = new File(list.get(list.size() - 1).getParent().toString());
                File destination = new File(directory + File.separator + name);
                try {
                    copyFile(fileToCopy, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void moveFiles(ListView<File> leftList, ListView<File> rightList,Button button) {
        copyFilesAndDirectories(leftList, rightList, button);
        deleteFile(button,leftList);

    }
    public void deleteFile(Button button, ListView<File> listView){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File fileToDelete=listView.getSelectionModel().getSelectedItem();
                fileToDelete.delete();
            }
        });
    }
    public void downloadButtonAction(ListView leftList, ListView rightList, FTPClient ftpClient){
        leftList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {

            getDownloadButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        String saveDir = oldValue.toString();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    downloadFile.download(rightList,saveDir,ftpClient);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }));

    }

    public void uploadButtonAction(ListView leftList, FTPClient ftpClient){
        getUploadButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String localFilePath = leftList.getSelectionModel().getSelectedItem().toString();
                    File file = new File(localFilePath);
                    String localParentDir = file.getParent();
                    String remoteDirPath = ftpClient.printWorkingDirectory();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                downloadFile.upload(ftpClient,leftList);
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

