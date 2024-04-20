package application.Controller;

import java.io.IOException;

import application.FXMLUtils;
import application.Model.SignupModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignupController {
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Label signupMessage;
	
	@FXML
	private Button previousButton;
	
	@FXML
	private Button signupConfirmBtn;
	
	
	public void signupButtonOnAction(ActionEvent event) throws IOException{
		if (firstNameField.getText().isBlank() == false && //Check if the field is blank
			lastNameField.getText().isBlank() == false &&
			usernameField.getText().isBlank() == false &&
			passwordField.getText().isBlank() == false) {
			
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			//Check if the user name is unique
			boolean isUsernameUnique = SignupModel.isUsernameUnique(username);
			
			if(isUsernameUnique) {
				SignupModel.createUser(firstName, lastName, username, password);
				
				signupMessage.setText("Account created successfully");
			} else {
				signupMessage.setText("Username has already exist");
			}
			
		} else {
			signupMessage.setText("Please fill the informations above");
		}
	}
	
	public void previousButtonOnAction(ActionEvent event) throws IOException{
		FXMLUtils.loadFXML("View/Login.fxml", previousButton);  //go back to Login page
	}
	
}
