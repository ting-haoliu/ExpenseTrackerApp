package application.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Income extends Transaction{
	private String type;

	public Income(String username, String type, double amount, LocalDate date, String detail) {
		this.username = username;
	    this.type = type;
	    this.amount = amount;
	    this.date = date;
	    this.detail = detail;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getTransactionDetails() {
		return "Income from " + type;
	}
	
	@Override
	public String toString() {
		// Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(date);
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = dateFormat.format(sqlDate);
    	
        return String.format("%s,%s,%s,%s,%s", username, type, String.valueOf(amount),
        										dateString, detail);
	}
}
