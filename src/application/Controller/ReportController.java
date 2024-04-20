package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Model.ExpenseModel;
import application.Model.IncomeModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class ReportController implements Initializable{
	
	@FXML
	private Label currentUserLabel;
	
	@FXML
	private PieChart incomePieChart;
	
	@FXML
	private PieChart expensePieChart;
	
	@FXML
	private ToggleButton changePieBtn;
	
	@FXML
	private Button goBackButton;
	
	@FXML
	private ChoiceBox<String> monthChoiceBox;
	
	private String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
		    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		monthChoiceBox.getItems().addAll(months);
		
		incomePieChart.setVisible(true);
		expensePieChart.setVisible(false);
	}
	
	public void monthChoiceBoxOnAction(ActionEvent event) throws IOException {
		String username = currentUserLabel.getText();

		String selectMonth = monthChoiceBox.getValue();
		int monthIndex = getMonthIndex(selectMonth) + 1;
		String[] incomeCategory = {"Salary", "Investment", "Interest", "Other"};
		String[] expenseCategory = {"Food", "Transportation", "Accommodation", "Entertainment",
											"Utilities", "Other"};
		
		// Clear existing data from the pie chart
		incomePieChart.getData().clear();
	    expensePieChart.getData().clear();
		
	    ObservableList<PieChart.Data> incomePieData =
				FXCollections.observableArrayList(
						new PieChart.Data("Salary", IncomeModel.getIncomeByCategory(username, monthIndex, incomeCategory[0])),
						new PieChart.Data("Investment", IncomeModel.getIncomeByCategory(username, monthIndex, incomeCategory[1])),
						new PieChart.Data("Interest", IncomeModel.getIncomeByCategory(username, monthIndex, incomeCategory[2])),
						new PieChart.Data("Other", IncomeModel.getIncomeByCategory(username, monthIndex, incomeCategory[3])));
		
		ObservableList<PieChart.Data> expensePieData =
				FXCollections.observableArrayList(
						new PieChart.Data("Food", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[0])),
						new PieChart.Data("Transportation", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[1])),
						new PieChart.Data("Accommodation", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[2])),
						new PieChart.Data("Entertainment", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[3])),
						new PieChart.Data("Utilities", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[4])),
						new PieChart.Data("Other", ExpenseModel.getExpenseByCategory(username, monthIndex, expenseCategory[5])));
	     
		incomePieData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : $", data.pieValueProperty())));
		expensePieData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : $", data.pieValueProperty())));
		
		incomePieChart.getData().addAll(incomePieData);
	    expensePieChart.getData().addAll(expensePieData);
	    incomePieChart.setStyle("-fx-font-size: 12px;");
	    expensePieChart.setStyle("-fx-font-size: 9px;");
	}
	
	public int getMonthIndex(String month) {
		for(int i = 0; i < months.length; i++) {
			if(months[i].equals(month)) {
				return i;
			}
		}
		return -1;
	}
	
	public void changePie(ActionEvent event) throws Exception{
		if (changePieBtn.isSelected()) {
			incomePieChart.setVisible(false);
			expensePieChart.setVisible(true);
			changePieBtn.setText("EXPENSE");
		} else {
			incomePieChart.setVisible(true);
	        expensePieChart.setVisible(false);
	        changePieBtn.setText("INCOME");
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
