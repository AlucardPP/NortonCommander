package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

public class SortByTypes implements Comparator<File> {
	private boolean order;

	public SortByTypes(boolean order) {
		this.order = order;
	}

	public int compare(File o1, File o2) {
		String fileType1 = "";
		String fileType2 = "";

		try {
			fileType1 = Files.probeContentType(o1.toPath());
			fileType2 = Files.probeContentType(o2.toPath());
		} catch (IOException ex) {

		}

		if (fileType1 == null) {
			fileType1 = "probeContentTypeIsNull";
		}
		if (fileType2 == null) {
			fileType2 = "probeContentTypeIsNull";
		}
		if (order) {
			return fileType1.compareTo(fileType2);
		} else {
			return fileType2.compareTo(fileType1);
		}
	}

}
