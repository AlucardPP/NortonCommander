package utils;

import java.io.File;
import java.util.Comparator;

public class SortByName implements Comparator<File> {
	private boolean order;

	public SortByName(boolean order) {
		this.order = order;
	}

	public int compare(File o1, File o2) {
		if (order) {
			return (o1.getName().compareTo((o2.getName())));
		} else {
			return (o2.getName().compareTo((o1.getName())));
		}
	}

}
