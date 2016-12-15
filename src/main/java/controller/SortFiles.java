package controller;

import javafx.beans.binding.Bindings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Alucard on 2016-12-09.
 */
public class SortFiles {
    ShowFiles showFiles = new ShowFiles();
    final Image unseleted = new Image(getClass().getResourceAsStream("/view/icon-arrow-up-b-32.png"));
    final Image selected = new Image(getClass().getResourceAsStream("/view/icon-arrow-down-b-32.png"));
    final ImageView toggleSelected = new ImageView();
    final ObservableList<String> choice = FXCollections.observableArrayList("", "Type of Element", "Name", "Last edit", "Size");

    public void showIconOnToggleButton(ToggleButton toggleButton) {
        toggleSelected.imageProperty().bind(Bindings.when(toggleButton.selectedProperty()).then(selected).otherwise(unseleted));
    }

    public File[] filesTOSort(ListView listView) {
        ObservableList observableList = listView.getItems();

        if (observableList.isEmpty()) {
            return null;
        } else {
            String path = observableList.get(observableList.size() - 1).toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
            return files;
        }
    }

    public void sortByType(ListView<File> listView, boolean order) {
        Comparator comparator = new SortByType(order);
        File[] files = filesTOSort(listView);
        Arrays.sort(files, comparator);
        showFiles.showListFile(files, listView);
    }

    public void sortByName(ListView<File> listView, boolean order) {
        Comparator comparator = new SortByName(order);
        File[] files = filesTOSort(listView);
        Arrays.sort(files, comparator);
        showFiles.showListFile(files, listView);
    }

    public void sortByLastEdit(ListView<File> listView, boolean order) {
        Comparator comparator = new SortByLastEdit(order);
        File[] files = filesTOSort(listView);
        Arrays.sort(files, comparator);
        showFiles.showListFile(files, listView);
    }

    public void sortBySize(ListView<File> listView, boolean order) {
        Comparator comparator = new SortBySize(order);
        File[] files = filesTOSort(listView);
        Arrays.sort(files, comparator);
        showFiles.showListFile(files, listView);
    }


}
