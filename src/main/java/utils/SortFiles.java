package utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SortFiles {
	

	ShowFile show = new ShowFile();
	final Image unseleted = new Image(getClass().getResourceAsStream("/view/icon-arrow-up-b-32.png"));
	final Image selected = new Image(getClass().getResourceAsStream("/view/icon-arrow-down-b-32.png"));
	public final static ImageView leftToggleSelected = new ImageView();
	public final static ImageView rightToggleSelected = new ImageView();
	public final static ObservableList<String> choice = FXCollections.observableArrayList("Type of Element", "Name",
			"Last edit", "Size");

	public void showIconOnToggleButton(ToggleButton toggleButton, ImageView toggleSelected) {
		toggleSelected.imageProperty()
				.bind(Bindings.when(toggleButton.selectedProperty()).then(selected).otherwise(unseleted));
	}

	public File[] filesTOSort(ListView<File> listView) {
		ObservableList<File> observableList = listView.getItems();

		if (observableList.isEmpty()) {
			return null;
		} else {
			File[] files = observableList.get(observableList.size() - 1).getParentFile().listFiles();

			return files;
		}
	}

	public void sortByType(ListView<File> listView, boolean order) {
		Comparator<File> comparator = new SortByTypes(order);
		File[] files = filesTOSort(listView);
		Arrays.sort(files, comparator);
		show.showFileList(listView, files);
	}

	public void sortByName(ListView<File> listView, boolean order) {
		Comparator<File> comparator = new SortByName(order);
		File[] files = filesTOSort(listView);
		Arrays.sort(files, comparator);
		show.showFileList(listView, files);
	}

	public void sortByLastEdit(ListView<File> listView, boolean order) {
		Comparator<File> comparator = new SortByLastEdit(order);
		File[] files = filesTOSort(listView);
		Arrays.sort(files, comparator);
		show.showFileList(listView, files);
	}

	public void sortBySize(ListView<File> listView, boolean order) {
		Comparator<File> comparator = new SortBySize(order);
		File[] files = filesTOSort(listView);
		Arrays.sort(files, comparator);
		show.showFileList(listView, files);
	}

}
