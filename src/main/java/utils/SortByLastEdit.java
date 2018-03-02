package utils;

import java.io.File;
import java.util.Comparator;

public class SortByLastEdit implements Comparator<File> {

	
	private boolean order;

	public SortByLastEdit(boolean order) {
		this.order = order;
	}

	public int compare(File o1, File o2) {
		if (order) {
			return Long.compare(o1.lastModified(), o2.lastModified());
		} else {
			return Long.compare(o2.lastModified(), o1.lastModified());
		}

	}
}
