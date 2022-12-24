package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationController {
	@FXML
	public TextField loginField;
	@FXML
	public PasswordField passwordField;
	@FXML
	public Button submitButton;

	public void login(ActionEvent actionEvent) throws IOException {
		if (loginField.getText().equals("admin") &&
				passwordField.getText().equals("admin")) {
			System.out.println("Authorized!");
			Node node = (Node) actionEvent.getSource();
			Stage dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/hello-view.fxml")));
			dialogStage.setScene(scene);
			dialogStage.show();
		}
	}
}
