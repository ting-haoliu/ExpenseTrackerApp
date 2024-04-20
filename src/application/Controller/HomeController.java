package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.FXMLUtils;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomeController implements Initializable{

	@FXML
	private Label currentUserLabel;
	
	@FXML
	private Label totalIncomeField;
	
	@FXML
	private Label totalExpenseField;

	@FXML
	private Button logoutButton;
	
	@FXML
	private Button switchToAddBtn;
	
	@FXML
	private Button switchToReportBtn;
	
	@FXML
	private Button incomeButton;
	
	@FXML
	private Button expenseButton;
	
	@FXML
	private TextArea incomeDataField;
	
	@FXML
	private TextArea expenseDataField;
	
	@FXML
	private ChoiceBox<String> monthChoiceBox;
	
	private String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
		    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		monthChoiceBox.getItems().addAll(months); //Set the months in the drop down menu
		
		incomeDataField.setFont(Font.font("monospace", FontWeight.NORMAL, 13));
		expenseDataField.setFont(Font.font("monospace", FontWeight.NORMAL, 13));
		incomeDataField.setVisible(true);
		expenseDataField.setVisible(false);
	
		totalExpenseField.setText("Expense: " + 0.0);		
		totalIncomeField.setText("Income: " + 0.0);
	}
	
	public void monthChoiceBoxOnAction(ActionEvent event) throws IOException {
		String selectMonth = monthChoiceBox.getValue();
		int monthIndex = getMonthIndex(selectMonth) + 1; //index start from 0

		incomeDataField.clear();
		expenseDataField.clear();
		
		IncomeModel.printIncomeByMonth(incomeDataField, currentUserLabel.getText(), monthIndex);
		ExpenseModel.printExpenseByMonth(expenseDataField, currentUserLabel.getText(), monthIndex);
		
		totalExpenseField.setText("Expense: " + ExpenseModel.getTotalExpenseByMonth(currentUserLabel.getText(), monthIndex));		
		totalIncomeField.setText("Income: " + IncomeModel.getTotalIncomeByMonth(currentUserLabel.getText(), monthIndex));
	}
	
	public int getMonthIndex(String month) {
		for(int i = 0; i < months.length; i++) {
			if(months[i].equals(month)) {
				return i;
			}
		}
		return -1;
	}
	
	public void incomeButtonOnAction(ActionEvent event) throws Exception{
		incomeDataField.setVisible(true);
	    expenseDataField.setVisible(false);
	    
	    System.out.println("Income button clicked");	       
	}
	
	public void expenseButtonOnAction(ActionEvent event) throws Exception{
		expenseDataField.setVisible(true);
        incomeDataField.setVisible(false);
        
        System.out.println("Expense button clicked");  
	}
	
	public void switchToAdd(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Add.fxml"));
			Parent root = loader.load();
			
			AddController addController = loader.getController();
			addController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) switchToAddBtn.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToRemove(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Remove.fxml"));
			Parent root = loader.load();
			
			RemoveController removeController = loader.getController();
			removeController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) switchToAddBtn.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToModify(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Modify.fxml"));
			Parent root = loader.load();
			
			ModifyController modifyController = loader.getController();
			modifyController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) switchToAddBtn.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToSetting(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Setting.fxml"));
			Parent root = loader.load();
			
			SettingController settingController = loader.getController();
			settingController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) switchToAddBtn.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToReport(ActionEvent event) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Report.fxml"));
			Parent root = loader.load();
			
			ReportController reportController = loader.getController();
			reportController.displayInfo(currentUserLabel.getText());
			
			Stage stage = (Stage) switchToReportBtn.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logoutButtonOnAction(ActionEvent event) throws IOException{
		FXMLUtils.loadFXML("View/Login.fxml", logoutButton);
	}

	public void displayInfo(String username) {
		currentUserLabel.setText(username);
	}
}
