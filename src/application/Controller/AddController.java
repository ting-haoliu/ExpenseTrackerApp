package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Model.ExpenseModel;
import application.Model.IncomeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController implements Initializable{
	
	@FXML
	private Label currentUserLabel;
	
	@FXML
	private Label addMessage;
	
	@FXML
	private Button goBackButton;
	
	@FXML
	private Button addButton;
	
	@FXML
	private ChoiceBox<String> typesBox;
	
	@FXML
	private ChoiceBox<String> categoryBox;
	
	@FXML
	private TextField amountField;
	
	@FXML
	private DatePicker dateField;
	
	@FXML
	private TextArea detailField;

	private String[] types = {"Income", "Expense"};
	private String[] incomeCategory = {"Salary", "Investment", "Interest", "Other"};
	private String[] expenseCategory = {"Food", "Transportation", "Accommodation", "Entertainment",
										"Utilities", "Other"};
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		typesBox.getItems().addAll(types);
		
		typesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			categoryBox.getItems().clear();

			if(newValue.equals("Income")) {
	            categoryBox.getItems().addAll(incomeCategory);
	        } else if (newValue.equals("Expense")) {
	        	categoryBox.getItems().addAll(expenseCategory);
	        }
	    });
	}
	
	public void addButtonOnAction(ActionEvent event) throws Exception{
		if (typesBox.getValue() != null &&
		    categoryBox.getValue() != null &&
		    amountField.getText().isBlank() == false &&
		    dateField.getValue() != null &&
		    detailField.getText().isBlank() == false) {
			
			String type = typesBox.getValue();

			String username = currentUserLabel.getText();
			String category = categoryBox.getValue();
			double amount = Double.parseDouble(amountField.getText());
			LocalDate date = dateField.getValue();
			String detail = detailField.getText();
			
			switch(type) {
				case "Income":
					IncomeModel.createIncome(username, category, amount, date, detail);
					addMessage.setText("Income Added");
					break;
				case "Expense":
					ExpenseModel.createExpense(username, category, amount, date, detail);
					addMessage.setText("Expense Added");
					break;
			}
			
		} else {
			addMessage.setText("Please fill the informations");
		}
	}
	
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
