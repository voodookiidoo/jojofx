package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
		// выгрузить fxml из файла
		Scene scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("MySuperApp");
		stage.setScene(scene);
		stage.show();
	}
}