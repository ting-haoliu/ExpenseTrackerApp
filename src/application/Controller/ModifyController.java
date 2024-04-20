package application.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModifyController {
	
	@FXML
	private Label currentUserLabel;
	
	@FXML
	private Button goBackButton;
	
	public void switchToHome(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Home.fxml"));
			Parent root = loader.load();
			
			HomeController homeController = loader.getController();
			homeController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) goBackButton.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void displayInfo(String username) {
		currentUserLabel.setText(username);
	}
}
