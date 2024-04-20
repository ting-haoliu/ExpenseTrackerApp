package application.Controller;

import java.io.IOException;

import application.FXMLUtils;
import application.Model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {
	
	@FXML
	private TextField nameTextfield;
	
	@FXML
	private PasswordField passwordTextfield;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button signupButton;
	
	@FXML
	private Label loginMessage;
	
	private Stage stage;
	
	
	public void loginButtonOnAction(ActionEvent event) throws IOException{
		if (nameTextfield.getText().isBlank() == false &&
			passwordTextfield.getText().isBlank() == false) {
			
			String username = nameTextfield.getText();
			String password = passwordTextfield.getText();
			boolean isValid = LoginModel.validateLogin(username, password);
			
			if(isValid) {
				switchToHome(username);
			} else {
				loginMessage.setText("Invalid Login. Please try again");
			}
		} else {
			loginMessage.setText("Please enter Username and Password");
		}
	}
	
	public void cancelButtonOnAction(ActionEvent event) {
		stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void signupButtonOnAction(ActionEvent event) throws IOException{
		FXMLUtils.loadFXML("View/Signup.fxml", signupButton);
	}
	
	public void switchToHome(String username) throws IOException{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Home.fxml"));
			Parent root = loader.load();
			
			HomeController homeController = loader.getController();
			homeController.displayInfo(username);
			
			Stage stage = (Stage) loginButton.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
