package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//TODO po ki chuj castujesz to ?
		Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Norton Commander");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
