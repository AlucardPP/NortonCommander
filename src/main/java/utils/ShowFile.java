package utils;

import java.io.File;



import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class ShowFile {



	public static void showDiskLetter(ComboBox<File> combo) {
		File[] roots = File.listRoots();
		for (File root : roots) {
			if (root.canRead()) {
				combo.getItems().add(root);
			}
		}
	}

	public void showFileList(ListView<File> list, File[] files) {


		list.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
			@Override
			public ListCell<File> call(ListView<File> list) {
                return new AttachmentListCell();
		}});

		list.getItems().clear();
		list.getItems().addAll(files);
	}

	//Rozwiązanie woła o pomstę do nieba!
	public static void moveToParentDirectory(ListView<File> list) {
		ObservableList<File> observableList = list.getItems();
		try {
			File parent = observableList.get(observableList.size() - 1).getParentFile().getParentFile();
			File[] files = parent.listFiles();
			list.getItems().clear();
			list.getItems().addAll(files);
		} catch (NullPointerException e) {
			// System.err.println("You can't move upper than disk letter!!");
			File parent = observableList.get(observableList.size() - 1).getParentFile();
			File[] files = parent.listFiles();
			list.getItems().clear();
			list.getItems().addAll(files);
		}

	}

	public void startingMethod(ComboBox<File> combo, ListView<File> list) {
		combo.getSelectionModel().selectFirst();
		File[] files = combo.getValue().listFiles();
		showFileList(list, files);
	}



//TODO rozbij to !
	private static class AttachmentListCell extends ListCell<File> {
		private ImageView exeImageView = new ImageView();
		private ImageView folderImageView = new ImageView();
		private ImageView txtImageView = new ImageView();
		private ImageView fileImageView = new ImageView();

		final Image folder = new Image(getClass().getResourceAsStream("/view/folder-close-icon.png"));
		final Image exeFile = new Image(getClass().getResourceAsStream("/view/File Format Exe-24x24.png"));
		final Image txtFile = new Image(getClass().getResourceAsStream("/view/Document-txt-icon.png"));
		final Image emptyFile = new Image(getClass().getResourceAsStream("/view/Document-empty-icon.png"));

		private final String[] txtExtension = new String[] { "txt", "xml", "html", "css", "ini" };
		private final String[] exeExtension = new String[] { "exe" };
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
				setText(null);
				setGraphic(null);
			}
			else if (item.isDirectory()) {
				folderImageView.setImage(folder);
				setText(item.toString());
				setGraphic(folderImageView);
			} else if (checkExtension(item, txtExtension) == true) {
				txtImageView.setImage(txtFile);
				setText(item.toString());
				setGraphic(txtImageView);
			} else if(checkExtension(item, exeExtension) == true){
				exeImageView.setImage(exeFile);
				setText(item.toString());
				setGraphic(exeImageView);
			} else {
				fileImageView.setImage(emptyFile);
				setText(item.toString());
				setGraphic(fileImageView);
			}
        }

		public boolean checkExtension(File file, String[] extension) {
			for (String check : extension) {
				if (file.getName().toLowerCase().endsWith(check)) {
					return true;
				}
			}
			return false;
		}
	}
}

