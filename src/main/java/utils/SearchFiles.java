package utils;

import java.io.File;


import javafx.scene.control.ListView;



public class SearchFiles {
	//TODO musimy wejść głębiej - 4 wcięcia to za mało! A teraz poważnie - do podziału!
	public static void search(String path, String fileToSearch, ListView<File> list) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files=file.listFiles();
			for (File f:files) {
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
/*
	public static void searchFile(String path, TextField textArea, ListView<File> listView) {
		textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					String fileToSearch = textArea.getText();
					search(path, fileToSearch, listView);
				}
				event.consume();
			}
		});
	}
*/
}
