package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.scene.control.ListView;

public class DownloadFile {

	public void download(ListView<File> list, String path, FTPClient client) throws IOException {

		FTPFile[] files = client.listFiles();
		OutputStream outputStream = null;
		InputStream inputStream = null;
		for (FTPFile file : files) {
			if (file.isFile()) {
				String name = list.getSelectionModel().getSelectedItem().toString();
				String pathToFile = client.printWorkingDirectory() + "/" + name;
				String destination = path + File.separator + name;
				File dest = new File(destination);
				outputStream = new BufferedOutputStream(new FileOutputStream(dest));
				inputStream = client.retrieveFileStream(pathToFile);
				byte[] bytesArray = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(bytesArray)) != -1) {
					outputStream.write(bytesArray, 0, bytesRead);
				}

				boolean success = client.completePendingCommand();
				if (success) {
					System.out.println("File has been downloaded successfully.");
				}

			} else if (file.isDirectory()) {
				download(list, path, client);

			}
			outputStream.close();
			inputStream.close();

		}

	}

	public void upload(FTPClient client, ListView<File> list) throws IOException {

		String fil = list.getSelectionModel().getSelectedItem().toString();
		File file = new File(fil);
		if (file.isFile()) {

			String name = file.getName();
			String ftp = client.printWorkingDirectory();
			String destination = ftp + "/" + name;
			InputStream inputStream = new FileInputStream(file);
			System.out.println("Start uploading file");
			OutputStream outputStream = client.storeFileStream(destination);
			byte[] bytesIn = new byte[4096];
			int read = 0;

			while ((read = inputStream.read(bytesIn)) != -1) {
				outputStream.write(bytesIn, 0, read);
			}
			inputStream.close();
			outputStream.close();

			boolean completed = client.completePendingCommand();
			if (completed) {
				System.out.println("The second file is uploaded successfully.");
			}

		}
	}

}
