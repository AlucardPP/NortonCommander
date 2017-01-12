package utils;

import java.io.File;
import java.util.Comparator;

public class SortBySize implements Comparator<File> {
	private boolean order;

	public SortBySize(boolean order) {
		this.order = order;
	}

	public int compare(File o1, File o2) {
		if (order) {
			return Long.compare(o1.length(), o2.length());
		} else {
			return Long.compare(o2.length(), o1.length());
		}
	}

}
