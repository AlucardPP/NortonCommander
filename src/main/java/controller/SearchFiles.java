package controller;

import javafx.scene.control.ListView;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;

/**
 * Created by Alucard on 2016-12-09.
 */
public class SearchFiles {


    public static void search(String path, String fileToSearch, ListView list) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                String fileString = f.getAbsolutePath().toString();
                if (f.isFile()) {
                    String filename = f.getName();
                    if (filename.contains(fileToSearch)) {
                        list.getItems().add(f);
                    }
                }
                search(fileString, fileToSearch, list);
            }
        }
    }

    public void searchFile(String path, TextField textArea, ListView listView) {
        textArea.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String file = textArea.getText();
                    listView.getItems().clear();
                    search(path, file, listView);
                }
            }
        });
    }
}
